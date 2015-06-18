package Actors.Replicators

import Actors.SystemActor
import Actors.Messages._
import akka.actor.{Props, ActorRef}
import java.util.Random
import akka.dispatch
import models.SQLStatementHelper.MutableSQLStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.mutable.ArrayBuffer

/**
 * this class is responsible for  overseeing the actual eventual consistency
 * process
 * @author Jack Davey
 * @version 16th June 2015
 */
class ReplicationOverSeer(logger:ActorRef) extends SystemActor(logger)
{
  var servers:ArrayBuffer[ActorRef] = ArrayBuffer()

  override  def preStart() =
  {
      for(index <- 0 to 3)
      {
        val server = context.actorOf(Props(new ReplicationServer(logger,index)))
        servers.insert(index,server)
      }
      updateReferenceLists

    scheduleNextConsistencyRun
  }

  def scheduleNextConsistencyRun: Unit = {
    context.system.scheduler.scheduleOnce(30 seconds)
    {
      makeConsistent()
    }
  }

  /**
   * method to choose a server at raodom and
   * send the  recently recieved SQL query to it
   * @param update the update in question
   */
  def processUpdate(update:MutableSQLStatement) =
  {
    println("im on fire")
    val rand = new Random()
    val serverId = rand.nextInt(3)
    servers(serverId) ! update
  }


  /**
   * very simple test method to ensure
   * that all the servers are wired up correctly.
   */
   def sendTestMessage =
   {
     val rand = new Random()
     val serverId = rand.nextInt(2)
     servers(serverId) ! TestMessage
   }

  def updateReferenceLists = servers.foreach((thing) => thing ! servers)

  def makeConsistent(): Unit =
  {
    println("making consistent")
    servers.foreach((server) => server ! MakeConsistent)
    scheduleNextConsistencyRun
  }


  def receive =
  {
    case query:UpdateTableStatment =>  processUpdate(query)
  }


}
