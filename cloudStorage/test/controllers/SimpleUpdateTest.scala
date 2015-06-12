package controllers

import play.api.libs.json.Json
import play.api.test.{PlaySpecification, FakeRequest, WithApplication}

/**
 * Created by jackdavey on 11/06/15.
 */
class SimpleUpdateTest extends  PlaySpecification
{
  "the databas query action " should
    {
      "execute a simple sql update and keep a record of it" in new WithApplication
      {
        val jsonString = "{\"queryType\":\"update\",\"queryContents\":\"update person set initials = 'e' where person_id = 120\"}"
        val json = Json.parse(jsonString)
        route(FakeRequest(controllers.routes.Application.saveMutableQuery()).withJsonBody(json))
        match
        {
          case Some(response) => status(response) must equalTo(OK)
            contentAsString(response) must contain( "operation sucseeded")
          case None => failure
        }
      }
    }
}
