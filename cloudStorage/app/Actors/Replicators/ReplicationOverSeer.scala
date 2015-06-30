package Actors.Replicators

import java.time.LocalDateTime

import Actors.{Messages, SystemActor}
import Actors.Messages._
import akka.actor.{Props, ActorRef}
import java.util.Random
import akka.dispatch
import akka.testkit.TestActorRef
import models.InconsistentQueryRecords
import models.QueryResultHelper.QueryResult
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
class ReplicationOverSeer(logger:ActorRef,replicationMarshaller: ActorRef) extends SystemActor(logger)
{
  var servers:ArrayBuffer[ActorRef] = ArrayBuffer()

  var timetilNextConsistencySweep = 180

  override  def preStart() =
  {
      for(index <- 0 to 3)
      {
        val server = context.actorOf(Props(new ReplicationServer(logger,index,replicationMarshaller)))
        servers.insert(index,server)
      }
      updateReferenceLists
    scheduleNextConsistencyRun
  }



  /**
   * this chedules the next run to make everything eventually
   * consistent on all the worker servers.
   */
  def scheduleNextConsistencyRun: Unit = {

    InconsistentQueryRecords.time = LocalDateTime.now().plusSeconds(timetilNextConsistencySweep)
    context.system.scheduler.scheduleOnce(timetilNextConsistencySweep seconds)
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
    servers(getRandomServerNumber) ! update
  }


  /**
   * very simple test method to ensure
   * that all the servers are wired up correctly.
   */
   def sendTestMessage =
   {
     val serverId: Int = getRandomServerNumber
     servers(serverId) ! TestMessage
   }

  def getRandomServerNumber: Int =
  {
    val rand = new Random()
    val serverId = rand.nextInt(3)
    println("chosen server" + serverId)
    serverId
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
    case query:MutableSQLStatement =>  processUpdate(query)
    case MakeConsistent => makeConsistent
    case results:QueryResult => servers(getRandomServerNumber) ! results
    case time:Int => timetilNextConsistencySweep = time
    case testData:ArrayBuffer[ActorRef] => servers = testData
              updateReferenceLists

  }


}
