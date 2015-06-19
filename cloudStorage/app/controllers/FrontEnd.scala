package controllers

import Actors.Commiters.DatabaseCommiterOverseer
import Actors.Messages.{MakeConsistent, RetrieveLog, TestMessage}
import akka.actor.Props
import akka.dispatch.Futures
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
import org.joda.time
import org.joda.time.Seconds
import scala.concurrent.Await
import akka.util.Timeout
import controllers.Application._
import models.CreateTableStatementHelpers.CreateTableStatement
import models.DripTableStatementHelper.DropTableStatement
import models.{LogHelper, SQLQuery}
import models.UpdateTableStatmentHelper.UpdateTableStatment
import play.api.libs.json._
import play.api.mvc._
import models.SQLQuery._
import play.api.db._
import play.api.Play.current
import models.SQLStatementHelper.{MutableSQLStatement, SQLStatement}
import anorm._
import play.libs.Akka
import Actors.Messages
import Actors.Logger
import Actors.Replicators._
import akka.pattern.ask
import scala.concurrent.duration.Duration
import akka.util.Timeout._
/**
 * this is the main controller
 * for the application
 * @author Jack Davey
 * @version 15th June 2015
 */
object FrontEnd  extends Controller
{
   val system = Akka.system()
   val loggingActor = system.actorOf(Props(new Logger))
   val databaseCommiter = system.actorOf(Props(new DatabaseCommiterOverseer(loggingActor)))
   val replicationMarshaller = system.actorOf(Props(new ReplicationMarshaller(loggingActor,databaseCommiter)))
   val repServer = system.actorOf(Props(new ReplicationOverSeer(loggingActor,replicationMarshaller)))

  def createTable = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[CreateTableStatement]
      statement.fold(
        errors =>
        {
          BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
        },
        goodStatement  =>
        {
          createOrDropTable(goodStatement)
        }
      )

  }

  def getLogOutput = Action
  {
    loggingActor ! Messages.Message("hi")
    Ok(LogHelper.jsonVersion)
  }

  def makeConsistent = Action
  {
    repServer ! MakeConsistent
    Ok("database fully consistent")
  }

  def delete = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[DeleteStatment]
      processUpdate(statement)
  }

  def insert = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[InsertStatment]
      processUpdate(statement)
  }

  def update = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[UpdateTableStatment]
      processUpdate(statement)
  }


  def processUpdate(statement: JsResult[MutableSQLStatement]) = {
    statement.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      goodStatement => {
        println("hitting it ")
        repServer ! goodStatement
        Ok("all ok here")
      }
    )
  }

  def dropTable = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[DropTableStatement]
      statement.fold(
        errors =>
        {
          BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
        },
        goodStatement  =>
        {
          createOrDropTable(goodStatement)
        }
      )

  }





  private def createOrDropTable(goodStatement:SQLStatement): Result =
  {
    try
    {
      DB.withConnection
      {
        implicit con =>
          val result = SQL(goodStatement.getNewSQLStatement).executeUpdate()
          Ok(s"$result all ok here")
      }
    }
    catch {
      case error: Exception => BadRequest(error.getMessage)
    }
  }
}
