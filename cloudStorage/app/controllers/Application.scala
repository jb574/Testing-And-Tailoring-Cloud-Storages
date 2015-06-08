package controllers

import models.SQLQuery
import play.api.libs.json._
import play.api.mvc._
import models.Book._
import models.SQLQuery._

object Application extends Controller {



  def testAction = Action
  {
    Ok("awesome, we got there ok!")
  }

  /**
   * the code below here is just amples from the orginal template
   * I used to set the project up
   * created by typesafe activator and will be removed
   * in a future release.
   */


  def listQueries = Action
  {
    Ok(SQLQuery.JsonVer)
  }



  def saveQuery = Action(BodyParsers.parse.json)
  {
    request =>
      val query = request.body.validate[SQLQuery]
      query.fold(
        errors =>
        {
          BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
        },
        query  =>
        {
          if(query.isValidQuery)
          {
            addQuery(query)
            Ok(Json.obj("status" -> "OK"))
          }
          else
          {
            BadRequest("this is not a valid query")
          }

        }
      )
  }

  def saveBook = Action(BodyParsers.parse.json) { request =>
    val b = request.body.validate[Book]
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      book => {
        addBook(book)
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
}
