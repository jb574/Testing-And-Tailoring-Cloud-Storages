package controllers

import java.sql.{Statement, Connection, ResultSet, SQLException}

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
   * method that
   * sends an sql query to the database
   * @param query the query to send
   * @return the output of tghe database transaction
   */
  private def queryDatabase(query:SQLQuery):String  =
  {
    try
    {
      val (connection: Connection, statement: Statement) = connectToDatabase
      val res = statement.executeQuery(query.queryContents)
      if(res != null)
      {
        val res = statement.getResultSet
        val metaData = res.getMetaData
        val colCount = metaData.getColumnCount
        var result = "operation sucseeded"
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
          "operation failed" + statement.getWarnings.toString
      }
    }
    catch
    {
      case error:SQLException => error.getMessage
    }
  }

  /**
   * method to preform the initial connection
   * to the database
   * @return  a database connection and a statement to run with
   */
  private def connectToDatabase: (Connection, Statement) =
  {
    val connection = DB.getConnection()
    val statement = connection.createStatement()
    (connection, statement)
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
      val (connection: Connection, statement: Statement) = connectToDatabase
      statement.executeUpdate(query.queryContents)
      "operation sucseeded"
    }
    catch
      {
        case error:SQLException => error.getMessage
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
          addQuery(query)
          Ok(queryDatabase(query))

        }
      )
  }

  def saveMutableQuery = Action(BodyParsers.parse.json)
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
          addQuery(query)
          Ok(sendToDatabase(query))
        }
      )
  }



}
