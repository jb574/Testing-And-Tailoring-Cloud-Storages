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
      implicit val timeout = Timeout(5 seconds)
      val res:Future[Any] =  servers(masterIndex) ? RequestVote
      res onSuccess
        {
          case properResults:Int =>
            if(!downServers.contains(properResults))
            {
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
    for (index <- 0 to servers.size)
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
    }
  }


  def receive =
  {
    case result:QueryResult =>
      implicit val timeout = Timeout(5 seconds)
      val res:Future[Any] =  servers(masterIndex) ? result
      res onSuccess
        {
          case properResults:QueryResult => sender ! properResults
        }
    case msg:Any => servers.foreach((server) => server ! msg)
    case voteChange:RequestVote =>
      holdVote
    case (nodeRank:Boolean, pos:Int ) =>
      val time = SettingsManager.retrieveValue("lifeTime")
      downServers = downServers + pos
      context.system.scheduler.scheduleOnce( time seconds)
      {
        downServers = downServers - pos
        if(nodeRank)
        {
          createServer(false,pos)
        }
        else
        {
          createServer(nodeRank,pos)
        }
      }

  }
}
