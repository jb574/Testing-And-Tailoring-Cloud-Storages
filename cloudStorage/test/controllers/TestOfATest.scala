package controllers
import play.api.test._

/**
 * test calss that allows me to
 * get to grips with the
 * specification
 * unit testing framework
 * that comes linked in with play,
 * @author Jack Davey
 * @version 10th June 2015
 */
class TestOfATest extends PlaySpecification
{
  "testAction Application" should
  {
    "print something" in new WithApplication
    {
      route(FakeRequest(controllers.routes.Application.testAction()))
      match
      {
        case Some(response) => status(response) must equalTo(OK)
      }
    }
  }
}
