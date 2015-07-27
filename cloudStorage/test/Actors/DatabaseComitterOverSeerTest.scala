package Actors

import play.api.test._
import play.libs.Akka
import akka.actor._
import akka.testkit._
import Actors.Commiters.{DatabaseCommiter, DatabaseCommiterOverseer}
import models.InsertStatmentHelper._

/**
 * Created by jackdavey on 24/06/15.
 */
class DatabaseComitterOverSeerTest  extends  PlaySpecification
{

  class TestSystem extends TestKit(ActorSystem("testSystem"))
  {
    val logger = TestActorRef[Logger]
    val comitter = TestActorRef(new DatabaseCommiterOverseer(logger))

    def test(): Boolean =
    {
      val statement = InsertStatment(List(),Map())
      val dataList = List(statement)
      comitter ! dataList
      comitter.underlyingActor.seenQueries.contains(statement)
    }

    def seenMoreThanOnceTest():Boolean =
    {
      val statement = InsertStatment(List(),Map())
      val dataList = List(statement)
      comitter ! dataList
      comitter ! dataList
      comitter.underlyingActor.seenQueries.size == 1
    }

  }

  "the database committer overseer should" should
  {
    "spawn a slave, send it on and keep a record of it" in new WithApplication
    {
      val testSystem:TestSystem  = new TestSystem
      assert(testSystem.test())
    }
  }
  "the database committer overseer " should
    {
      "not process each query more than once" in new WithApplication
      {
        val testSystem:TestSystem  = new TestSystem
        assert(testSystem.seenMoreThanOnceTest())
      }
    }


}
