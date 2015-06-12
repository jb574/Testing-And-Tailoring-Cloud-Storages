package controllers

import play.api.test._
import play.api.libs.json._


/**
 * class to test the list functionality
 * @author Jack Davey
 * @version 11th June 2015
 */
class ListTest extends PlaySpecification
{
  "the listAction" should
    {
      "display a json list" in new WithApplication
      {
        route(FakeRequest(controllers.routes.Application.listQueries()))
        match
        {
          case Some(response) => status(response) must equalTo(OK)
                                 contentAsString(response) must contain("{")
          case None => failure
        }
      }
    }
}
