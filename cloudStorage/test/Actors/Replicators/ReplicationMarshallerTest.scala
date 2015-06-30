package Actors.Replicators

import java.time.LocalDateTime

import Actors.Commiters.DatabaseCommiterOverseer
import Actors.Commiters.DatabaseCommiter
import Actors.Logger
import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import models.InsertStatmentHelper.InsertStatment
import models.QuerySet
import models.SQLStatementHelper.MutableSQLStatement
import play.api.test.{WithApplication, PlaySpecification}
import scala.collection.mutable.ArrayBuffer

/**
 * Created by jackdavey on 25/06/15.
 */
class ReplicationMarshallerTest extends  PlaySpecification
{
  class TestSystem extends TestKit(ActorSystem("testSystem"))
  {
    val logger = TestActorRef[Logger]
    val commiterOverseer = TestActorRef(new DatabaseCommiter(logger ))
    val marshaller = TestActorRef(new ReplicationMarshaller(logger,commiterOverseer))

    def passingOnQueriesTest():Boolean =
    {
      var msg = new ArrayBuffer[QuerySet]
      val update = new InsertStatment(List(), Map())
      val query = new QuerySet(update, 1)
      msg.append(query)
      marshaller ! msg
      marshaller.underlyingActor.queries.contains(update)
    }





  }

  "the ReplicationMarshaller " should
  {
    "send any ArrayBuffer messages it gets straight onto the database server to be comitted" in new WithApplication()
    {
      val system = new TestSystem
      system.passingOnQueriesTest()
    }
  }



}