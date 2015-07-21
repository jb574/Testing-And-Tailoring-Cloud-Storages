package Actors.Replicators

import Actors.Messages._
import Actors.SystemActor
import akka.actor._
import akka.pattern.AskSupport
import controllers.SettingsManager
import models.QueryResultHelper.QueryResult
import models.{QuerySet, InconsistentQueryRecords}
import models.SQLStatementHelper.MutableSQLStatement
import java.time.{LocalDateTime, LocalDate}
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap
import Actor._
import akka._
import akka.actor.ActorContext

import scala.util.Random

/**
 * this represents one replication server
 * @author Jack Davey
 * @version 16th June 2015
 */
class ReplicationServer(logger:ActorRef,id:Int,replicationMarshaller:ActorRef,var master:Boolean,
                        val clusterOverseer:ActorRef, val avIndex:Int) extends SystemActor(logger)
  with AskSupport
{





  println(s"master is $master")
    var otherServers:ArrayBuffer[ActorRef] = ArrayBuffer()
    var inconsistentUpdates:List[QuerySet] = List()
   var masterList:List[String]  = List()
    var noSeen = 0
  val FailureHandle = context.actorSelection("akka://application/user/$e")
  var avServers:ArrayBuffer[ActorRef] = ArrayBuffer()
  var seenMaster = true
  runSafetyCheck
  /**
   *method that announces that a message has been recieved
   * by this server
   */
  private def respondToTestMessage =
  {
    println(" message received by server " + id)
    logger ! Message(" message recieved by server $id")
  }

  def scheduleNextMasterCheckup =
  {
    val time = SettingsManager.retrieveValue("timeTilNextConsistencySweep")
    context.system.scheduler.scheduleOnce(time  seconds)
    {
      runSafetyCheck()
    }
  }

  def  runSafetyCheck =
  {
    if(!master)
    {
       if(seenMaster)
       {
         seenMaster = false
         avServers.foreach((server) => server ! ConcernedHealthRequest)
         scheduleNextMasterCheckup
       }
      else
       {
            clusterOverseer ! RequestVote
       }
    }
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
    if (chance > SettingsManager.retrieveValue("chanceOfGoodResult"))
    {
      respondToTestMessage
      InconsistentQueryRecords.addItem(update.getNewSQLStatement)
      if (!inconsistentUpdates.exists((clockSeq) => clockSeq.addNewQuery(update, id)))
      {
        inconsistentUpdates = new QuerySet(update, id) :: inconsistentUpdates
        println(inconsistentUpdates.size)
      }
    }
    else
    {
      FailureHandle ! (false,update)
      inconsistentUpdates.foreach((clock) =>
        clock.queries.foreach((query) => FailureHandle ! (false,update)  ) )
      clusterOverseer ! (master,avIndex)
      context.stop(self)
    }
  }

  /**
   * method to send all inconsistent updates to all other
   * replication severs, to check that
   * they haven't seen anything mor erecent
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
   * method to merge two querysets together if they both go together,
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
   * merge an extenral query in from the rest of this system
   * @param first the query to merge
   * @param mergedQueries  an arraybuffer to collec tthe results to send to the database
   */
 private def mergeOneExternalQuery(first:QuerySet,sender:ActorRef): Unit =
 {
   inconsistentUpdates.foreach((query) => mergeOneQuery(first,query,sender))
 }

  /**
   * this is the recieve block for this actor
   * if a MutableSQL query arrives
   * then we processe it, we just announce testmesages
   * if we get a severlist then we eplace our existing
   * list with it. a makeConsistent request causes us to
   * distribute all our pieces of  data to all other requests. if we recieve
   * such a list of queries, then we  maeke it consistent and send the results onto the database.
   */
  def standardOperation: Receive =
  {

    case update:MutableSQLStatement => processNewQuery(update)
    case TestMessage => respondToTestMessage
    case serverList:ArrayBuffer[ActorRef] => otherServers = serverList
    case (true, avList:ArrayBuffer[ActorRef]) => avServers = avList
    case MakeConsistent =>
      if(master)
      {
        InconsistentQueryRecords.clear()
        distributeUpdates
        noSeen = 0
        println("starting consistency sweeep now")
      }
      context.become(mergeMode)
    case queryResult:QueryResult => inconsistentUpdates.foreach(update => update.executeQuery(queryResult))
      sender ! queryResult
    case ConcernedHealthRequest =>
      if(master)
      {
        sender ! MasterAlive
      }
    case MasterAlive =>
      seenMaster = true
    case WonVote =>
      master = true
    case RequestVote =>
      val rand = new Random()
      sender ! rand.nextInt(avServers.size)
  }

  def mergeMode:Receive =
  {
    case foreignQueries:List[QuerySet] =>
      println("checking foriegn queries")
      if(master)
      {
        makeConsistent(foreignQueries,sender)
        noSeen = noSeen + 1
        println("doing consistency run for this node")
        if(noSeen == 2)
        {
          replicationMarshaller ! inconsistentUpdates
          println("ferrying everything back to the standard mode")
          inconsistentUpdates = List()
          context.become(standardOperation)
        }
      }
    case (false, set:QuerySet) =>
      println("removing old nodes ")
      inconsistentUpdates = inconsistentUpdates.filter(
        (current) => !set.equals(current))
    case queryResult:QueryResult => inconsistentUpdates.foreach(update => update.executeQuery(queryResult))
      queryResult.markComplete
      println("all done!")
      sender ! queryResult
    case false =>
      if(master)
      {
        sender ! true
      }
    case true =>
      seenMaster = true
  }

  def receive = standardOperation

  /**
   * this is the main code that makes everything consistent
   * @param foreignQUeries the list of queries we recieved from all other replicas
   */
  def makeConsistent(foreignQUeries: List[QuerySet],sender:ActorRef): Unit =
  {
    foreignQUeries.foreach(query => mergeOneExternalQuery(query, sender))
  }
}
