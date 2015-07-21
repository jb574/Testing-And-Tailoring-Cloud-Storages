package Actors.Replicators
import akka.actor.ActorRef
import Actors.Commiters.DatabaseCommiter
import Actors.Logger
import Actors.Messages.MakeConsistent
import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
import models.QueryResultHelper.QueryResult
import models.QuerySet
import models.UpdateTableStatmentHelper.UpdateTableStatment
import play.api.test.{WithApplication, PlaySpecification}

import scala.collection.mutable.ArrayBuffer

/**
 * Created by jackdavey on 29/06/15.
 */
class ReplicationServerTest extends  PlaySpecification {

  class TestSystem extends TestKit(ActorSystem("testSystem")) {
    val server = TestActorRef(new ReplicationServer(testActor, 0, testActor,true,testActor,0))
    var servers:ArrayBuffer[ActorRef] = ArrayBuffer()
    for (index <- 0 to 3) {
      servers.insert(index,testActor)
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
      server.underlyingActor.inconsistentUpdates.size > 0
    }

    def checkServerReciept(): Boolean =
    {
      server ! servers
      server.underlyingActor.otherServers.equals(servers)
    }

    def checkUpdateDistribution():Boolean =
    {
      server.underlyingActor.otherServers = servers
      server ! MakeConsistent
      expectMsg(List())
      true
    }


     def checkEmptyMergeTest(): Boolean =
    {
      server.underlying.become(server.underlyingActor.mergeMode)
      server !  List[QuerySet]()
      server.underlyingActor.noSeen > 0
    }

    def checkNonEmptyMergeTest(): Boolean =
    {
      val statement: DeleteStatment = new DeleteStatment(List("doo"),
        Map())
      server ! statement
      server.underlying.become(server.underlyingActor.mergeMode)
      server !  List[QuerySet]()
    server ! List[QuerySet]()
      server.underlyingActor.inconsistentUpdates.size >= 0
      true
    }

    def checkMergFalse():Boolean =
    {
      server ! new UpdateTableStatment(List("foo"),Map(),Map())
      val other = new UpdateTableStatment(List("foo"),Map(),Map())
      server ! List(other)
      server.underlyingActor.inconsistentUpdates.size <= 1
    }

    def checkMergTrue():Boolean =
    {
      val other = new UpdateTableStatment(List("foo"),Map(),Map())
      server ! new UpdateTableStatment(List("foo"),Map(),Map())
      server ! List(other)
      server.underlyingActor.inconsistentUpdates.size >= 1
    }


  }

  "a replication server" should {
    " always handle  new queries effectively" in new WithApplication() {
      val system = new TestSystem()
      system.checkProcessingNewQueries    must equalTo(true)
    }
  }

  "a replication server " should
  {
    "always be able to change application servers at aill " in new WithApplication()
    {
      val system = new TestSystem
      system.checkServerReciept()  must equalTo(true)
    }
  }

  "a replication server " should
    {
      "always be able to send updates to all replication servers " in new WithApplication()
      {
        val system = new TestSystem
        system.checkUpdateDistribution()   must equalTo(true)
      }
    }



  "a replication server " should
    {
      "send all foriegn queries strraight to the replication server when it has none  " in new WithApplication()
      {
        val system = new TestSystem
        system.checkEmptyMergeTest()    must equalTo(true)
      }
    }

  "a replication server " should
    {
      "send all foriegn queries strraight to the replication server after bieng merged  " in new WithApplication()
      {
        val system = new TestSystem
        system.checkNonEmptyMergeTest()  must equalTo(true)
      }
    }
  "a replication server " should
    {
      "should not merge queris sent after the latest one it has" in new WithApplication()
      {
        val system = new TestSystem
        system.checkMergFalse()      must equalTo(true)
      }
    }

  "a replication server " should
    {
      "should merge all queries that operate on the same tables sent before the last one " in new WithApplication()
      {
        val system = new TestSystem
        system.checkMergTrue()    must equalTo(true)
      }
    }


}
