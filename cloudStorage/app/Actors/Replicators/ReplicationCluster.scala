package Actors.Replicators
import scala.concurrent.ExecutionContext.Implicits.global
import Actors.Messages.{WonVote, RequestVote}
import Actors.SystemActor
import akka.actor.{Props, ActorRef}
import akka.pattern.AskSupport
import akka.util.Timeout
import controllers.SettingsManager
import models.QueryResultHelper.QueryResult
import scala.concurrent.duration._
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import Actors.Messages.Message

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
  override  def preStart() =
  {
    println("hitting prestart")
    var masterChosen = true
    createServer(true,0)
    for(index <- 1 to SettingsManager.retrieveValue("secServers"))
    {
      createServer(false,index)
    }
    servers.foreach((server) => server ! (true,servers))
  }

  def createServer(master:Boolean,index:Int) =
  {
    val server = context.actorOf(Props(new ReplicationServer(logger,id,replicationMarshaller,master,self,index)))
    servers.insert(index,server)
  }


  def requestVote(index:Int): Unit =
  {
    var  done = false
    while(!done)
    {
      if(!downServers.contains(index))
      {
        implicit val timeout = Timeout(5 seconds)
        val res:Future[Any] =  servers(index) ? RequestVote
        res onSuccess
          {
            case properResults:Int =>
              done = true
              if(votes.contains(index))
              {
                votes = votes + (index -> 0)
              }
              else
              {
                votes = votes + (index -> (votes(index) + 1))
              }

          }
      }
    }

  }


  def holdVote =
  {
    logger ! Message("holding vote")
    votes = Map()
    for (index <- 0 to servers.size-1)
    {
      requestVote(index)
    }
    var biggest = 0
    var winner = -1
    for ((key, value) <- votes)
    {
      if (value > biggest)
      {
        biggest = value
        winner = key
      }
      servers(winner) ! WonVote
      masterIndex = winner
      logger ! Message(s"for cluster $id, the new master is $masterIndex")
    }
  }


  def receive =
  {
    case result:QueryResult =>
      println("running query")
      implicit val timeout = Timeout(5 seconds)
      val res =  servers(masterIndex) ? result
      res onSuccess
        {
          case properResults:QueryResult => sender !  properResults
        }
      println("so the result  is " + result.toString )
    case msg:Any =>
      for(index <- 0 to servers.size-1)
      {
        if(!downServers.contains(index))
        {
          servers(index) ! msg
        }
      }
    case voteChange:RequestVote =>
      logger ! Message("starting vote")
      holdVote
    case (nodeRank:Boolean, pos:Int ) =>
      val time = SettingsManager.retrieveValue("lifeTime")
      downServers = downServers + pos
      context.system.scheduler.scheduleOnce( time seconds)
      {
        downServers = downServers - pos
        servers(pos) ! false
      }

  }
}
