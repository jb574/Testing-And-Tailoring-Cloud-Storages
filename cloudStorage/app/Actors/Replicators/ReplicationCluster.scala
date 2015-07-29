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
 * to simulate basic availibility
 * @author Jack Davey
 * @version 17th july 2015
 */
class ReplicationCluster (logger:ActorRef,id:Int,replicationMarshaller:ActorRef) extends SystemActor(logger)
with AskSupport
{

  var votes:Map[Int,Int]  = Map()
  var servers:ArrayBuffer[ActorRef] = ArrayBuffer()
  var masterIndex = 0
  var downServers:Set[Int] = Set()
  var seenMaster = true

  def scheduleNextMasterCheckup:Unit =
  {
    val time = SettingsManager.retrieveValue("checkUpTime")
    context.system.scheduler.scheduleOnce(time  seconds)
    {
      runSafetyCheck
    }
  }

  def  runSafetyCheck:Unit =
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




  override  def preStart() =
  {
    self ! 1
  }

  def initServer: Unit =
  {
    println("hitting prestart")
    var masterChosen = true
    createServer(true, 0)
    for (index <- 1 to SettingsManager.retrieveValue("secServers"))
    {
      createServer(false, index)
    }
    servers.foreach((server) => server !(true, servers))
    scheduleNextMasterCheckup
  }

  def createServer(master:Boolean,index:Int) =
  {
    val server = context.actorOf(Props(new ReplicationServer(logger,id,replicationMarshaller,master,index)))
    servers.insert(index,server)
  }


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


  def holdVote =
  {
    var masterChosen = false
    while (!masterChosen)
    {
      logger ! Message("holding vote")
      votes = Map()
      var winner = -1
      for (index <- servers.indices)
      {
        requestVote(index)
      }
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
      if(!(downServers.contains(winner))  && winner != -1)
      {
        servers(winner) ! WonVote
        masterIndex = winner
        logger ! Message(s"for cluster $id, the new master is $masterIndex")
        masterChosen = true
      }

    }

  }


  def receive =
  {
    case 1 => initServer
    case result:QueryResult =>
      println("running query")
      implicit val timeout = Timeout(5 seconds)
      val res =  servers(masterIndex) ? result
      res onSuccess
        {
          case properResults:QueryResult => sender !  properResults
        }
      println("so the result  is " + result.toString )

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
    case msg:TestMessage => passMessageOn(msg)
    case serverList:ArrayBuffer[ActorRef] => passMessageOn(serverList)
    case (true, avList:ArrayBuffer[ActorRef]) =>  passMessageOn((true,avList))
    case msg:MakeConsistent => passMessageOn(msg)
    case foreignQueries: List[QuerySet] => passMessageOn(foreignQueries)
    case (false, set: QuerySet) => passMessageOn((false,set))
  }

  def passMessageOn(msg: Any): Unit =
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
