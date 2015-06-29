package Actors.Replicators

import Actors.Commiters.DatabaseCommiter
import Actors.Logger
import Actors.Messages.MakeConsistent
import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
import models.QueryResultHelper.QueryResult
import models.QuerySet
import play.api.test.{WithApplication, PlaySpecification}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by jackdavey on 29/06/15.
 */
class ReplicationServerTest extends  PlaySpecification {

  class TestSystem extends TestKit(ActorSystem("testSystem")) {
    val logger = TestActorRef[Logger]
    val marshaller = TestActorRef(new ReplicationMarshaller(logger, logger))
    val server = TestActorRef(new ReplicationServer(logger, 0, marshaller))
    val overseer = TestActorRef(new ReplicationOverSeer(logger, marshaller))
    var servers: ArrayBuffer[TestActorRef[ReplicationServer]] = ArrayBuffer()
    for (index <- 0 to 3) {
      val server = TestActorRef(new ReplicationServer(logger, index, marshaller))
      servers.insert(index, server)
    }

    def checkProcessingNewQueries: Boolean = {
      val statement: DeleteStatment = new DeleteStatment(List("doo"),
        Map())
      server ! statement
      assert(server.underlyingActor.inconsistentUpdates.size > 0)
      val statement2: DeleteStatment = new DeleteStatment(List("doo"),
        Map())
      server ! statement2
      assert(server.underlyingActor.inconsistentUpdates.size < 2)
      val statement3: DeleteStatment = new DeleteStatment(List("aaa"),
        Map())
      server ! statement3
      server.underlyingActor.inconsistentUpdates.size > 1
    }

    def checkServerReciept(): Boolean = {
      server ! servers
      server.underlyingActor.otherServers.equals(server)
    }

    def checkUpdateDistribution():Boolean =
    {
      server ! servers
      server ! MakeConsistent
      servers.forall((other) => other.underlying.mailbox.hasMessages)
    }

    def checkQueryExecution():Boolean =
    {
      val res = new QueryResult()
      server ! res
      res.isDone
    }


  }

  "a replication server" should {
    " always handle  new queries effectively" in new WithApplication() {
      val system = new TestSystem()
      system.checkProcessingNewQueries
    }
  }

  "a replication server " should
  {
    "always be able to change application servers at aill " in new WithApplication()
    {
      val system = new TestSystem
      system.checkServerReciept()
    }
  }

  "a replication server " should
    {
      "always be able to send updates to all replication servers " in new WithApplication()
      {
        val system = new TestSystem
        system.checkUpdateDistribution()
      }
    }


  "a replication server " should
    {
      "be able to proces reuests for  information  " in new WithApplication()
      {
        val system = new TestSystem
        system.checkQueryExecution()
      }
    }





}
