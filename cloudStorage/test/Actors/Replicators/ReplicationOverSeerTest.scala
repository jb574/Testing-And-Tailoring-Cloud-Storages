package Actors.Replicators

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
     *
     * @return true or false as to whether timer values are processed
     */
    def timerTest():Boolean =
    {
       overseer ! 42
      overseer.underlyingActor.timetilNextConsistencySweep == 42
    }

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

    def ensuereMessageRecieved[Thing,ActorType <: Actor](testActorRef:TestActorRef[ActorType]):Boolean =
    {
        val mailbox = testActorRef.underlying.mailbox
       while(mailbox.hasMessages)
       {
         val msg = mailbox.dequeue().message
         if(msg.isInstanceOf[Thing])
         {
           return true
         }
       }
       false
    }


    def sendMessage(server:TestActorRef[ReplicationServer]):Boolean =
    {
      server.underlying.mailbox.hasMessages
    }

    def checkALlServersReachable():Boolean =
    {
      overseer.underlyingActor.updateReferenceLists
      servers.forall((thing) => sendMessage(thing))
    }

    def checkConsistencyMessages:Boolean =
    {
      overseer.underlyingActor.makeConsistent()
      servers.forall((server) => ensuereMessageRecieved[MakeConsistent,ReplicationServer](server))
    }

    def checkUpdateRecievedByOneServer():Boolean =
    {
      val statement = new UpdateTableStatment(List(),Map(),Map())
      overseer.underlyingActor.processUpdate(statement)
      servers.exists((server) => ensuereMessageRecieved[MutableSQLStatement,ReplicationServer](server))
    }

    def checkResultRecievedByOneServer():Boolean =
    {
      val statement = new QueryResult()
      overseer ! statement
      servers.exists((server) => ensuereMessageRecieved[QueryResult,ReplicationServer](server))
    }
    
    

  }

  "the Replicationoverseers " should
    {
      "should change the amount of time it waits between consistency checks in recieving an int" in new WithApplication()
      {
        val system = new TestSystem
        system.timerTest()
      }
    }

  "the Replicationoverseers " should
    {
      "should only be able to send messages to valid server ids" in new WithApplication()
      {
        val system = new TestSystem
        system.canOnlyValidIDsBeSent()
      }
    }

  "the Replicationoverseers " should
    {
      "should only be able to reach all servers it knows about" in new WithApplication()
      {
        val system = new TestSystem
        system.checkALlServersReachable()
      }
    }

  "the Replicationoverseers " should
    {
      "make sure that all servers recieve consistency requests" in new WithApplication()
      {
        val system = new TestSystem
        system.checkConsistencyMessages
      }
    }


  "the Replicationoverseers " should
    {
      "make sure thatone server recieves each databae update" in new WithApplication()
      {
        val system = new TestSystem
        system.checkResultRecievedByOneServer()
      }
    }

  "the Replicationoverseers " should
    {
      "make sure thatone server recieves each databae result" in new WithApplication()
      {
        val system = new TestSystem
        system.checkResultRecievedByOneServer()
      }
    }






}
