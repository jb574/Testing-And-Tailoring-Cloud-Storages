package controllers
import play.api.test._
import play.api.libs.json._

/**
 * Created by jackdavey on 11/06/15.
 */
class SimpleQueryTest  extends PlaySpecification
{
  "the databas query action " should
    {
      "execute a simple sql query and keep a record of it" in new WithApplication
      {
        val jsonString = "  {\"queryType\":\"insert\",\"queryContents\":\"select * from patient\"}"
        val json = Json.parse(jsonString)
        route(FakeRequest(controllers.routes.Application.saveQuery()).withJsonBody(json))
        match
        {
          case Some(response) => status(response) must equalTo(OK)
            contentAsString(response) must contain( "operation sucseeded")
          case None => failure
        }
      }
    }
}
