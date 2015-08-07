package Actors.Replicators

import models.{QuerySet, InconsistentQueryRecords}
import models.SQLStatementHelper.MutableSQLStatement

import scala.concurrent.ExecutionContext.Implicits.global
import Actors.Messages._
import Actors.SystemActor
import akka.actor.{Props, ActorRef}
import akka.pattern.AskSupport
import akka.util.Timeout
import controllers.SettingsManager
import models.QueryResultHelper.QueryResult
import scala.concurrent.duration._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.concurrent.Await

/**
 * this is a replication cluster of several servers
 * to simulate basic availability
 * @author Jack Davey
 * @version 17th july 2015
 * @param logger  a reference to the logger actor
 * @param id  the server id
 * @param replicationMarshaller  the replication marshaller to pass
 *                               on to the child servers
 */
class ReplicationCluster (logger:ActorRef,id:Int,replicationMarshaller:ActorRef) extends SystemActor(logger)
with AskSupport
{

  var votes:Map[Int,Int]  = Map()
  var servers:ArrayBuffer[ActorRef] = ArrayBuffer()
  var masterIndex = 0
  var downServers:Set[Int] = Set()
  var seenMaster = true

  /**
   * method for scheduling the next master checkup
   *
   */
  def scheduleNextMasterCheckup:Unit =
  {
    val time = SettingsManager.retrieveValue("checkUpTime")
    context.system.scheduler.scheduleOnce(time  seconds)
    {
      runSafetyCheck
    }
  }

  /**
   *  method called at set intervals to check that the
   *  current master is still alive and elect a new master
   *  if not
   */
  def  runSafetyCheck =
  {
      if(seenMaster)
      {
        logger ! Message(" seen master node for " +
          s"consistency server $id, checking again")
        seenMaster = false
        servers.foreach((server) => server ! ConcernedHealthRequest)
        scheduleNextMasterCheckup
      }
      else
      {
        logger ! Message("the master hasn't reported in in a while," +
          "electing a new master")
        seenMaster = true
        holdVote
        scheduleNextMasterCheckup
      }
  }


  /**
   * actions to be performed
   * on server startup
   */
  override  def preStart() =
  {
    initServer
  }


  /**
   * method that sets up the
   * starting state
   * of the replication cluster
   */
  def initServer =
  {
    createChildServers
    servers.foreach((server) => server !(true, servers))
    scheduleNextMasterCheckup
  }

  /**
   * method to create the children servers
   */
  def createChildServers =
  {
    createServer(true, 0)
    for (index <- 1 to SettingsManager.retrieveValue("secServers"))
    {
      createServer(false, index)
    }
  }

  /**
   * method for creating an individual server
   * @param master a boolean to indicate whether this
   *               new server is the master or not
   * @param index  the position inside the internal
   *               arraybuffer at which this server is going
   *               to be inserted
   */
  def createServer(master:Boolean,index:Int) =
  {
    val server = context.actorOf(Props(new ReplicationServer(logger,id,replicationMarshaller,master,index)),s"cluster_$id,_server_$index")
    servers.insert(index,server)
  }


  /**
   * method to request a vote for a new
   * master from a particular server
   * @param index  the index of the vote in question.
   */
  def requestVote(index:Int): Unit =
  {
        implicit val timeout = Timeout(5 seconds)
        val res:Future[Any] =  servers(index) ? RequestVote
        val properResults = Await.result(res,5 seconds).asInstanceOf[Int]
        println(s"server $index voted for $properResults")
        if(properResults != -1)
        if(votes.contains(properResults))
        {
          votes = votes + (index -> (votes(properResults) + 1))
        }
        else
        {
          votes = votes + (properResults -> 1)
        }

  }

  /**
   * method to hold a vote for a new
   * master
   */
  def holdVote =
  {
    var masterChosen = false
    while (!masterChosen)
    {
      var winner: Int = gatherVotes
      var biggest = 0
      for ((key, value) <- votes)
      {
        println(s"node $key has $value votes ")
        if (value > biggest)
        {
          biggest = value
          winner = key
        }
      }
      if(!downServers.contains(winner)  && winner != -1)
      {
        servers(winner) ! WonVote
        masterIndex = winner
        logger ! Message(s"for cluster $id, the new master is $masterIndex")
        masterChosen = true
      }

    }

  }


  def gatherVotes: Int =
  {
    logger ! Message("holding vote")
    votes = Map()
    var winner = -1
    for (index <- servers.indices)
    {
      requestVote(index)
    }
    winner
  }

  def receive =
  {
    case result:QueryResult =>
      println("running query")
      implicit val timeout = Timeout(5 seconds)
      val res =  servers(masterIndex) ? result
      Await.result(res,5 seconds)
      var finalRes = new QueryResult()
      res onSuccess
        {
          case properResults:QueryResult => finalRes = properResults
        }
      println(s"at the cluster stage we have ${finalRes.toString}")
      println("so the result  is " + result.toString )
      sender ! finalRes

    case voteChange:RequestNewVote =>
      logger ! Message("starting vote")
      holdVote
    case (nodeRank:Boolean, pos:Int ) =>
      logger ! Message(s"noted that server $pos is down for cluster " +
        s"$id")
      val time = SettingsManager.retrieveValue("lifeTime")
      downServers = downServers + pos
      println(s"downservers for cluster $id contains $downServers")
      context.system.scheduler.scheduleOnce( time seconds)
      {
        downServers = downServers - pos
        println(s"downservers for cluster $id contains $downServers")
        servers(pos) ! false
      }
    case MasterAlive =>
      logger ! Message("we've seen the master")
      seenMaster = true
    case update:MutableSQLStatement => passMessageOn(update)
    case test:TestMessage => passMessageOn(test)
    case serverList:ArrayBuffer[ActorRef] => servers.foreach((serv) => serv ! serverList)
    case (true, avList:ArrayBuffer[ActorRef]) =>  passMessageOn((true,avList))
    case msg:MakeConsistent =>
      println("got to cluster level ")
      passMessageOn(msg)
    case foreignQueries: List[QuerySet] => passMessageOn(foreignQueries)
    case (false, set: QuerySet) => passMessageOn((false,set))
    case any => passMessageOn(any)
  }

  /**
   *  sends the message on to all the child nodes
   * @param msg  the message to pass on
   */
  def passMessageOn(msg: Any)=
  {
    for (index <- 0 to servers.size - 1)
    {
      if (!downServers.contains(index))
      {
        servers(index) ! msg
      }
    }
  }
}
