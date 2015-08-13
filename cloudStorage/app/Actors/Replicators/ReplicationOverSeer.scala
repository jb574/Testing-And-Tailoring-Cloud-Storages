package Actors.Replicators

import java.time.LocalDateTime
import controllers.{BackEnd, SettingsManager}

import scala.util.Success
import scala.util.Failure
import Actors.{Messages, SystemActor}
import Actors.Messages._
import akka.actor.FSM.Failure
import akka.actor.{Props, ActorRef}
import java.util.Random
import akka.dispatch
import akka.pattern.AskSupport
import akka.testkit.TestActorRef
import akka.util.Timeout
import models.{BasicAvailStatsGenerator, InconsistentQueryRecords}
import models.QueryResultHelper.QueryResult
import models.SQLStatementHelper.MutableSQLStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import  akka.util.Timeout._

import scala.collection.mutable.ArrayBuffer

/**
 * this class is responsible for overseeing the actual eventual consistency
 * process
 * @author Jack Davey
 * @version 16th June 2015
 * @param logger the logging actor for monitoring purposes
 * @param replicationMarshaller the replication marshaller actor to pass on to the actual
 *                              servers
 */
class ReplicationOverSeer(logger:ActorRef,replicationMarshaller: ActorRef) extends SystemActor(logger)
with AskSupport
{
  var servers:ArrayBuffer[ActorRef] = ArrayBuffer()


  /**
   * initialization code that runs
   * whenever the actor is started
   * it creates the child servers
   */
  override  def preStart() =
  {
      for(index <- 0 to SettingsManager.retrieveValue("primServers"))
      {
        val server = context.actorOf(Props(new ReplicationCluster(logger,index,replicationMarshaller)),s"cluster_$index")
        servers.insert(index,server)
      }
      updateReferenceLists
    scheduleNextConsistencyRun
  }



  /**
   * this schedules the next run to make everything eventually
   * consistent on all the worker servers.
   */
  def scheduleNextConsistencyRun:Unit =
  {
    val time = SettingsManager.retrieveValue("timeTilNextConsistencySweep")
    InconsistentQueryRecords.time = LocalDateTime.now().plusSeconds(time)
    context.system.scheduler.scheduleOnce(time  seconds)
    {
      makeConsistent()
    }
  }



  /**
   * method to choose a server at random and
   * send the recently received SQL query to it
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

  /**
   * @return   a random server number
   *            to choose a server with
   */
  def getRandomServerNumber: Int =
  {
    val rand = new Random()
    val serverId = rand.nextInt(SettingsManager.retrieveValue("primServers"))
    println("chosen server" + serverId)
    serverId
  }

  /**
   * sends the list of servers to all the children
   */
  def updateReferenceLists = servers.foreach((thing) => thing ! servers)

  /**
   * makes the whole system consistent
   */
  def makeConsistent() =
  {
    println("making consistent")
    servers.foreach((server) => server ! MakeConsistent)
    scheduleNextConsistencyRun
  }


  /**
   * retrieves one value from a server
   * @param server the server to get the value from
   * @param resSet the Set[String] in which to store the result
   * @param result  the queryset from which to obtain the result
   * @return the updated set
   */
  def getOneValue(server:ActorRef,resSet:Set[String],result:QueryResult):Set[String] =
  {
    val queryRes = processQuery(new QueryResult(result))
     resSet + ("|" + queryRes.toString + "|")
  }

  /**
   * receive block that determines actors behaviour
   */
  def receive =
  {
    case query:MutableSQLStatement =>  processUpdate(query)
    case MakeConsistent => makeConsistent
    case (true, result:QueryResult) =>
      var resSet:Set[String] = Set()
       servers.foreach(
         (server) => resSet = getOneValue(server,resSet,result))
      sender ! resSet
    case results:QueryResult =>
      val updatedResultss:QueryResult = processQuery(results)
      println(s"were sending ${updatedResultss.toString}")
      BackEnd.correctResult = updatedResultss.toString
      sender ! updatedResultss
    case testData:ArrayBuffer[ActorRef] => servers = testData
              updateReferenceLists
    case Flush =>
      servers.foreach((server) => server ! Flush)
  }

  /**
   * method to process a new query
   * @param result  the data returned from the database
   * @return  the data with some updates applied to it
   */
  def processQuery(result: QueryResult):QueryResult =
  {
    var finalResult = new QueryResult()
    implicit val timeout = Timeout(5 seconds)
    val res = servers(getRandomServerNumber) ? result
    Await.result(res,5 seconds)
    res onSuccess
    {
      case results:QueryResult => finalResult = results
    }
    println(s"from this function we will return ${finalResult.toString}")
   finalResult
  }



}
