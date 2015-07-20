package Actors.Replicators

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestActorRef, TestKit}
import models.InsertStatmentHelper.InsertStatment
import models.QuerySet
import play.api.test._

import scala.collection.mutable.ArrayBuffer

/**
 * Created by jackdavey on 20/07/15.
 */
class ReplicationClusterTest extends PlaySpecification
{
  class TestSystem extends TestKit(ActorSystem("testSystem"))
  {
    val cluster = TestActorRef(new ReplicationCluster(testActor,0,testActor))
    var servers:ArrayBuffer[ActorRef] = ArrayBuffer()
    for (index <- 0 to 3) {
      servers.insert(index,testActor)
    }
    cluster.underlyingActor.servers = servers
    def checkMessageForwarding():Boolean =
    {
      cluster ! 42
      expectMsg(42)
      true
    }




  }
  "a replication cluster" should
  {
    "forward all messages it recieves" in new WithApplication()
    {
      val testSystem = new TestSystem
      testSystem.checkMessageForwarding() must equalTo(true)
    }
  }
}
