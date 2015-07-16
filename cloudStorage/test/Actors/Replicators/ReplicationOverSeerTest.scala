package Actors.Replicators
import scala.concurrent.duration._
import Actors.Commiters.DatabaseCommiterOverseer
import Actors.{Messages, Logger}
import Actors.Messages.MakeConsistent
import akka.actor.{ActorRef, Props, ActorSystem,Actor}
import akka.testkit.{TestActorRef, TestKit}
import models.InsertStatmentHelper.InsertStatment
import models.QueryResultHelper.QueryResult
import models.SQLStatementHelper.MutableSQLStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment
import play.api.test.{PlaySpecification, WithApplication}
import scala.collection.mutable.ArrayBuffer
import akka.testkit.TestProbe
/**
 * Created by jackdavey on 25/06/15.
 */
class ReplicationOverSeerTest  extends PlaySpecification
{
  class TestSystem extends TestKit(ActorSystem("testSystem"))
  {
    val logger = TestActorRef[Logger]
    val comitter = TestActorRef(new DatabaseCommiterOverseer(logger))
    val marshaller = TestActorRef(new ReplicationMarshaller(logger,comitter))
    val overseer = TestActorRef(new ReplicationOverSeer(logger,marshaller))
    var servers:ArrayBuffer[TestActorRef[ReplicationServer]]  = ArrayBuffer()
    for(index <- 0 to 3)
    {
      val server = TestActorRef(new ReplicationServer(logger,index,marshaller))
      servers.insert(index,server)
    }
     overseer ! servers


    /**
     * method to check that messges can only be sent to
     * servers with valid server ids
     */
    def canOnlyValidIDsBeSent():Boolean =
    {
      var count = 0
      val actor = overseer.underlyingActor
      while(count < 1000)
      {
        val id = actor.getRandomServerNumber
        if(id < 0 || id > 2)
        {
          return  false
        }
        count = count + 1
      }
       true
    }

    def ensuereMessageRecieved[ActorType <: Actor](testActorRef:TestActorRef[ActorType]):Boolean =
    {
      val probe = TestProbe()
      probe watch testActorRef
      try
      {
        probe.expectMsgClass(MakeConsistent.getClass)
      }
      catch
      {
        case error:Exception => return false
      }

      true
    }


    def sendMessage(server:TestActorRef[ReplicationServer]):Boolean =
    {
      this.awaitCond(server.underlying.mailbox.hasMessages, 5 seconds)
      true
    }

    def checkALlServersReachable():Boolean =
    {
      val otherServers = overseer.underlyingActor.servers
      overseer.underlyingActor.updateReferenceLists
      servers.forall((thing) => thing.underlyingActor.otherServers == otherServers)
    }

    def checkConsistencyMessages:Boolean =
    {
      overseer.underlyingActor.makeConsistent()
      servers.forall((server) => ensuereMessageRecieved(server))
    }

    def checkUpdateRecievedByOneServer():Boolean =
    {
      val statement = new UpdateTableStatment(List(),Map(),Map())
      overseer.underlyingActor.processUpdate(statement)
      servers.exists((server) => ensuereMessageRecieved(server))
    }

    def checkResultRecievedByOneServer():Boolean =
    {
      val statement = new QueryResult()
      overseer ! statement
      servers.exists((server) => ensuereMessageRecieved(server))
    }
    
    

  }



  "the Replicationoverseers " should
    {
      "should only be able to send messages to valid server ids" in new WithApplication()
      {
        val system = new TestSystem
        system.canOnlyValidIDsBeSent()  must equalTo(true)
      }
    }

  "the Replicationoverseers " should
    {
      "should only be able to reach all servers it knows about" in new WithApplication()
      {
        val system = new TestSystem
        system.checkALlServersReachable()  must equalTo(true)
      }
    }

  "the Replicationoverseers " should
    {
      "make sure that all servers recieve consistency requests" in new WithApplication()
      {
        val system = new TestSystem
        system.checkConsistencyMessages must equalTo(true)
      }
    }


  "the Replicationoverseers " should
    {
      "make sure thatone server recieves each databae update" in new WithApplication()
      {
        val system = new TestSystem
        system.checkResultRecievedByOneServer()  must equalTo(true)
      }
    }

  "the Replicationoverseers " should
    {
      "make sure thatone server recieves each databae result" in new WithApplication()
      {
        val system = new TestSystem
        system.checkResultRecievedByOneServer()  must equalTo(true)
      }
    }






}
