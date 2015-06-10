package controllers

import java.sql.{ResultSet, SQLException}

import models.SQLQuery
import play.api.libs.json._
import play.api.mvc._
import models.SQLQuery._
import play.api.db._
import play.api.Play.current
object Application extends Controller {


  /**
   * very simple action i wrote to
   * test that my setup was working OK
   * @return just a sucsess mesage
   */
  def testAction = Action
  {
    Ok("awesome, we got there ok!")
  }


  /**
   * a list of all the queries recieved
   * @return  the list mentioned above in
   *          JSON format
   */
  def listQueries = Action
  {
    Ok(SQLQuery.JsonVer)
  }

  /**
   * method that composes
   * a suitable error message
   * to display to the end user
   * @param error the SQLException that caused the error
   * @return a string containing all
   *         information related to the error
   */
 private def composeErrorMessage(error:SQLException):String =
 {
   var res = error.getErrorCode.toString + " "
   res = res + error.getSQLState
   res + error.getCause.toString + error.getMessage
 }


  def produceTableResults(result:ResultSet)
  {

  }


  /**
   * method that
   * sends an sql query to the database
   * @param query the query to send
   * @return the output of tghe database transaction
   */
  private def sendToDatabase(query:SQLQuery):String  =
  {
    try
    {
      val connection = DB.getConnection()
      val statement = connection.createStatement();
      val querySucsessful = statement.execute(query.queryContents)
      if(querySucsessful)
      {
        val res = statement.getResultSet
        val metaData = res.getMetaData
        val colCount = metaData.getColumnCount
        var result = ""
        var counter = 1
        while(res.next())
        {
          for (index <- 1 to colCount )
          {
            result = result + " " + metaData.getColumnName(index)
            result = result + " " + res.getString(index)
          }
        }
        connection.close()
        result
      }
      else
      {
        statement.getWarnings.toString
      }
    }
    catch
    {
      case error:SQLException => composeErrorMessage(error)
    }
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
            Ok(sendToDatabase(query))
          }
          else
          {
            BadRequest("this is not a valid query")
          }

        }
      )
  }


}
