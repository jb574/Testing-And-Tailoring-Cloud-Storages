package Actors

import akka.actor.ActorSystem.Settings
import akka.dispatch.{Dispatchers, Mailboxes}
import akka.event.{LoggingAdapter, EventStream}
import play.api.test._
import akka.testkit._
import akka.testkit.TestActor
import akka.actor._
import Actors.Commiters.DatabaseCommiter
import play.libs.Akka
import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.Duration


/**
 * class that tests whether the database
 * comitter is working correctly.
 * we don't test that the databse connector iteslf works
 * because that would be dificult to test in unit tests
 * @author Jack Davey
 * @version 24th June 2015
 */
class DatabaseCommiterTest  extends PlaySpecification
{

  class TestSystem extends TestKit(ActorSystem("testSystem"))
  {
    val logger = TestActorRef[Logger]
    val comitter = TestActorRef(new DatabaseCommiter(logger))

    def test(): Boolean =
    {
      var result = false
      try
      {
        comitter ! "ok"
        false
      }
      catch
      {
        case good:IllegalArgumentException => true
      }
    }
  }


  "the database committer" should
  {
    "throw an illegalArgumentException when it gets a message it doesn't" +
      "know how to handle" in new WithApplication()
    {
       val system = new TestSystem()
       system.test()
    }
  }
}
