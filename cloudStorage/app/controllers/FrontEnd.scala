package controllers

import Actors.Commiters.DatabaseCommiterOverseer
import Actors.Messages.{MakeConsistent, RetrieveLog, TestMessage}
import akka.actor.Props
import akka.dispatch.Futures
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
import models.SelectStatementHelper.SelectStatement
import org.joda.time
import org.joda.time.Seconds
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future, Await}
import controllers.Application._
import models.CreateTableStatementHelpers.CreateTableStatement
import models.DripTableStatementHelper.DropTableStatement
import models.{InconsistentQueryRecords, LogHelper, SQLQuery}
import models.UpdateTableStatmentHelper.UpdateTableStatment
import play.api.libs.json._
import akka.util.Timeout
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
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import models.QueryResultHelper.QueryResult
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
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

  /**
   * this is the sevice to create new tables in the database
   */
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

  /**
   * this simply retrieves the output of the log
   */
  def getLogOutput = Action
  {
    loggingActor ! Messages.Message("hi")
    Ok(LogHelper.jsonVersion)
  }

  /**
   * service to keep track of all
   * inconsistent records in the
   * database.
   */
  def getInconsistentUPdateInfo = Action
  {
    Ok(InconsistentQueryRecords.getUserOutPut())
  }


  /**
   *  sends a request to make the qhole system consistent
   */
  def makeConsistent = Action
  {
    repServer ! MakeConsistent
    Ok("database fully consistent")
  }

  /**
   * deletes some data from the database
   */
  def delete = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[DeleteStatment]
      processUpdate(statement)
  }

  /**
   *inserts some data into the database
   */
  def insert = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[InsertStatment]
      processUpdate(statement)
  }


  /**
   *updates some data into the databse
   */
  def update = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[UpdateTableStatment]
      processUpdate(statement)
  }

  /**
   * method to process a mutable sql update
   * @param statement  the update in question
   * @return a mesage saying whther this was a sucsess
   */
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

  def changeTImeSweep(updatedTime:Int) = Action
  {
    repServer ! updatedTime
    Ok("time updated")
  }


 def runEventuallyConsistentQuery = Action.async(BodyParsers.parse.json)
 {

     request =>
      val query = request.body.validate[SelectStatement]
     query.fold(
       errors =>
       {

         Future(BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors))))
       },
       goodQuery =>
       {
         Future
         {
           var queryResult: QueryResult = queryDatabase(goodQuery)
           implicit val timeout = Timeout(5 seconds)
           val res:Future[Any] = repServer ? queryResult
           res onSuccess
             {
               case properResults:QueryResult =>  queryResult = properResults
             }
           Ok(Json.toJson(queryResult.toString))
         }
       }
     )
   }



  def addRow(queryResult:QueryResult,row:Row): Unit = {
    val rawData = row.asMap
    var betterRow: mutable.Map[String, String] = Map()
    for ((key:String, value:Option[Any]) <- rawData)
    {
      val index = key.indexOf(".")
      var betterKey = key.substring(index+1)
      betterRow = betterRow + (betterKey -> value.get.toString)
    }
    queryResult.addRow(betterRow)
  }

  def queryDatabase(goodQuery: SelectStatement):QueryResult = {
    val queryResult: QueryResult = new QueryResult(goodQuery.retrieveTableInfo())
    println(goodQuery.getNewSQLStatement)
    DB.withConnection {
      implicit con =>
        val result: List[Row] = SQL(goodQuery.getNewSQLStatement)().toList
        val workableResults: ArrayBuffer[mutable.Map[String, String]] = ArrayBuffer()
        result.foreach(row => addRow(queryResult,row))
    }
    queryResult
  }

  /**
   * as above for createTable
   */
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


  /**
   * this is the method that acually does the legwork for create or dop table.
   *
   * @param goodStatement the table in question
   * @return  a htpresult that tells you whether it works or not.
   */
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
