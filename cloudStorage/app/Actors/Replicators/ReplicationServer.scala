package Actors.Replicators
import scala.concurrent.duration._
import Actors.Messages._
import Actors.SystemActor
import akka.actor._
import akka.pattern.AskSupport
import controllers.SettingsManager
import models.QueryResultHelper.QueryResult
import models.{BasicAvailStatsGenerator, QuerySet, InconsistentQueryRecords}
import models.SQLStatementHelper.MutableSQLStatement
import java.time.{LocalDateTime, LocalDate}
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap
import Actor._
import akka._
import akka.actor.ActorContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

/**
 * this represents one replication server
 * @author Jack Davey
 * @version 16th June 2015
 * @param logger  a reference to the logger actor
 * @param id the server id for this server
 * @param replicationMarshaller  the place to send completed work to
 * @param master   the cluster master for this server
 * @param avIndex  the position of this server in the cluster
 */
class ReplicationServer(logger:ActorRef,id:Int,replicationMarshaller:ActorRef,var master:Boolean, val avIndex:Int) extends SystemActor(logger)
  with AskSupport
{




   val clusterOverseer:ActorRef = context.parent
  println(s"master is $master")
    var otherServers:ArrayBuffer[ActorRef] = ArrayBuffer()
    var inconsistentUpdates:List[QuerySet] = List()
   var masterList:List[String]  = List()
    var noSeen = 0
  val FailureHandle = context.actorSelection("akka://application/user/availibilityChecker")
  var avServers:ArrayBuffer[ActorRef] = ArrayBuffer()
  var seenMaster = true
  /**
   *method that announces that a message has been received
   * by this server
   */
  private def respondToTestMessage =
  {
    logger ! Message(s"message recieved by consistency server $id, av server $avIndex")
  }



  /**
   * method that processes new updates upon arrival at the
   * replication server
   * @param update  the new update in question
   */
  private def processNewQuery(update:MutableSQLStatement) =
  {
    val rand = new Random()
    val chance = rand.nextInt(100)
    println("chance is " + chance)
    val target =   SettingsManager.retrieveValue("chanceOfGoodResult")
    if (chance <= target)
    {
      logger ! Message(s"recieved message ok by server $avIndex")
      FailureHandle ! Update(true,update)
      respondToTestMessage
      InconsistentQueryRecords.addItem(update.getNewSQLStatement)
      if (!inconsistentUpdates.exists((clockSeq) => clockSeq.addNewQuery(update, id)))
      {
        inconsistentUpdates = new QuerySet(update, id) :: inconsistentUpdates
      }
    }
    else
    {

      logger ! Message(s"this message brought  server $avIndex down")
      FailureHandle ! Update(false,update)
      if(master)
      {
        logger ! Message(s"sever $avIndex was the master")
      }
      inconsistentUpdates = List()
      context.parent ! (master,avIndex)
      context.become(dead)
    }
  }

  def dead:Receive =
  {
    case revival:Boolean =>
      master = revival
      logger ! Message(s"cluster $avIndex server $id is back!")
      context.become(standardOperation)
    case RequestVote =>
      voteForMaster
    case _ =>
  }

  /**
   * method to send all inconsistent updates to all other
   * replication servers, to check that
   * they haven't seen anything more recent
   */
  private def distributeUpdates =
  {
    for(index <- 0 to SettingsManager.retrieveValue("primServers"))
    {
       if(index != id)
       {
         otherServers(index) ! inconsistentUpdates
       }
    }
  }

  /**
   * method to merge two query sets together if they both go together,
   * one should go before the other
   * @param first  the first queryset
   * @param second the second queryset
   * @param mergedQueries an arraybuffer containing the results
   *                      to send to the database
   */
 def mergeOneQuery(first:QuerySet,second:QuerySet, sender:ActorRef): Unit =
 {
   if(second.canBeMeged(first))
   {
     println("merging now")
     second.mergeQuerySets(first)
     sender ! (false,first)
   }
 }

  /**
   * merge an external query in from the rest of this system
   * @param first the query to merge
   * @param mergedQueries  an arraybuffer to collect the results to send to the database
   */
 private def mergeOneExternalQuery(first:QuerySet,sender:ActorRef): Unit =
 {
   inconsistentUpdates.foreach((query) => mergeOneQuery(first,query,sender))
 }

  def standardOperation:Receive = standardOperationMain orElse permanantWork

  /**
   * this is the receive block for this actor
   * if a MutableSQL query arrives
   * then we process it, we just announce testmessages
   * if we get a serverlist then we replace our existing
   * list with it. a makeConsistent request causes us to
   * distribute all our pieces of data to all other requests. if we receive
   * such a list of queries, then we make it consistent and send the results onto the database.
   */
  def standardOperationMain: Receive =
  {
    case update:MutableSQLStatement => processNewQuery(update)
    case TestMessage => respondToTestMessage
    case serverList:ArrayBuffer[ActorRef] => otherServers = serverList
    case (true, avList:ArrayBuffer[ActorRef]) => avServers = avList
    case MakeConsistent =>
      avServers.foreach((server) => server ! StragglersRequest)
      makeConsistent
  }


  /**
   * method that performs the job of making everything
   * consistent
   */
  def makeConsistent =
  {
    if (master)
    {
      InconsistentQueryRecords.clear()
      distributeUpdates
      noSeen = 0
      println("starting consistency sweeep now")
    }
    else
    {
      avServers.foreach((server) => server ! CatchupRequest(inconsistentUpdates))
    }
    context.become(mergeMode)
  }

  def permanantWork:Receive =
  {
    case msg:Flush => inconsistentUpdates = List()
    case StragglersRequest => sender ! StragglersResponse(inconsistentUpdates)
    case response:StragglersResponse =>
      response.response.foreach( (set) =>
        if(!inconsistentUpdates.contains(set))
        {
           val option = inconsistentUpdates.find((otherSet) =>  otherSet.canBeMeged(set))
          if(option.isDefined)
          {
            option.get.mergeQuerySets(set)
          }
          else
          {
             inconsistentUpdates =  set :: inconsistentUpdates
          }
        }
      )
    case queryResult:QueryResult => inconsistentUpdates.foreach(update => update.executeQuery(queryResult))
      sender ! queryResult
      println(s"at the start the result is ${queryResult.toString}")
    case ConcernedHealthRequest =>
      respondToConcernedHealthRequest
    case WonVote =>
      master = true
    case RequestVote =>
      voteForMaster
    case other => list:CatchupRequest =>
      list.others.foreach((set) => addMissingQuery(set) )

  }

  /**
   * if we are the master, this method tells the server that we are all ok
   */
  def respondToConcernedHealthRequest: Unit =
  {
    if (master)
    {
      sender ! MasterAlive
    }
  }

  /**
   * method to check if a query from one of the
   * slave servers is missing and add it if it's not
   * @param set the queryset to check
   */
  def addMissingQuery(set: QuerySet)=
  {
    if (!inconsistentUpdates.contains(set))
    {
      inconsistentUpdates = set :: inconsistentUpdates
    }
  }

  def voteForMaster: Unit =
  {
    println("generating vote")
    val rand = new Random()
    sender ! rand.nextInt(avServers.size)
  }

  def mergeMode:Receive = mergeModeMain orElse permanantWork

  def mergeModeMain:Receive =
  {
    case foreignQueries: List[QuerySet] =>
      println("checking foriegn queries")
      if (master)
      {
        makeConsistent(foreignQueries, sender)
        noSeen = noSeen + 1
        println("doing consistency run for this node")
        if (noSeen == 2)
        {
          replicationMarshaller ! inconsistentUpdates
          println("ferrying everything back to the standard mode")
          inconsistentUpdates = List()
          context.become(standardOperation)
        }
      }
    case (false, set: QuerySet) =>
      println("removing old nodes ")
      inconsistentUpdates = inconsistentUpdates.filter(
        (current) => !set.equals(current))
  }

  def receive = standardOperation

  /**
   * this is the main code that makes everything consistent
   * @param foreignQUeries the list of queries we received from all other replicas
   */
  def makeConsistent(foreignQUeries: List[QuerySet],sender:ActorRef): Unit =
  {
    foreignQUeries.foreach(query => mergeOneExternalQuery(query, sender))
  }
}
