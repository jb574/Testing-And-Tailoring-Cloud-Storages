package controllers

import java.lang

import Actors.Commiters.DatabaseCommiterOverseer
import Actors.Messages.{Flush, MakeConsistent, RetrieveLog, TestMessage}
import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Kill, ActorRef, Props}
import akka.dispatch.Futures
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
import models.SelectStatementHelper.SelectStatement
import org.joda.time
import org.joda.time.Seconds
import scala.collection.mutable
import scala.concurrent.{Promise, ExecutionContext, Future, Await}
import controllers.Application._
import models.CreateTableStatementHelpers.CreateTableStatement
import models.DropTableStatementHelper.DropTableStatement
import models.{BasicAvailStatsGenerator, InconsistentQueryRecords, LogHelper, SQLQuery}
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
import Actors.{ActorUtils, Messages, Logger}
import Actors.Replicators._
import akka.pattern.ask
import scala.concurrent.duration.Duration
import akka.util.Timeout._
import scala.collection.mutable.ArrayBuffer
import models.QueryResultHelper.QueryResult
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
/**
 * this is the main controller
 * for the application
 * @author Jack Davey
 * @version 15th June 2015
 */
object BackEnd  extends Controller
{

  var system = Akka.system()
  var loggingActor = system.actorOf(Props(new Logger), "logger")
  var databaseCommiter = system.actorOf(Props(new DatabaseCommiterOverseer(loggingActor)), "databaseComitterOverseer")
  var replicationMarshaller = system.actorOf(Props(new ReplicationMarshaller(loggingActor, databaseCommiter)), "replicationMarshaller")
  var repServer = system.actorOf(Props(new ReplicationOverSeer(loggingActor, replicationMarshaller)), "replicationOverseer")
  var checker = system.actorOf(Props(new AvailabilityChecker(repServer, loggingActor)), "availibilityChecker")
  var correctResult: String = ""

  def shutActors =
  {
    loggingActor ! Restart
    databaseCommiter ! Restart
    replicationMarshaller ! Restart
    repServer ! Restart
    checker ! Restart
  }




  println(checker.path)
  /**
   * this is the service to create new tables in the database
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
   * sends a request to make the whole system consistent
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

  def flush(): Unit =
  {
    shutActors
  }

  def flushAction = Action
  {
    flush()
    Ok("all flushed")
  }


  /**
   * inserts some data into the database
   */
  def insert = Action(BodyParsers.parse.json)
  {
    request =>
      val statement = request.body.validate[InsertStatment]
      processUpdate(statement)
  }


  /**
   * updates some data into the database
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
   * @return a message saying whether this was a success
   */
  def processUpdate(statement: JsResult[MutableSQLStatement]) = {
    statement.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      goodStatement => {
        BasicAvailStatsGenerator.informNewPacketArrival(goodStatement)
        println("hitting it ")
        InconsistentQueryRecords.addItem(goodStatement.toString)
        repServer ! goodStatement
        Ok("all ok here")
      }
    )
  }


  /**
   * retrieves stats for basic availability
   */
  def getAvailibilityStats =Action
  {
    Ok(BasicAvailStatsGenerator.getDisplayableResults())
  }


  /**
   * allows for changing a property in the system
   * @param name the name of the property
   * @param value the value of the property
   * */
  def changeProperty(name:String,value:Int) = Action
  {
    if(value > 0)
    {
        SettingsManager.updateKey(name,value)
        Ok(s"value $name updated to $value")
    }
     else
    {
      BadRequest(s"the new value for $name must be greater than 0")
    }
  }


  /**
   * returns all possible results for a query
   */
  def getAllPossibilities = Action.async(BodyParsers.parse.json)
  {
    GetInfoFromDatabase(true)
  }

  /**
   * runs an sql query in the system
   */
 def runEventuallyConsistentQuery = Action.async(BodyParsers.parse.json)
 {
     GetInfoFromDatabase(false)
 }




  /**
   *  this is used by the two methods above
   *  to run queries in the system
   * @param allPossibleNeeded    a boolean indicating whether we need
   *                             all possibilities or not
   */
  def GetInfoFromDatabase(allPossibleNeeded:Boolean):(Request[JsValue]) => Future[SimpleResult] =
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
            println(s"just got response from database and it is" +
              s" ${queryResult.toString}")
            if(allPossibleNeeded)
            {
              val set:Set[String] = ActorUtils.sendMessage((true,queryResult),repServer)
              Ok(set.toString())
            }
            else
            {
              var result = ""
              while(result == "")
              {
                val updates:QueryResult = ActorUtils.sendMessage(queryResult,repServer)
                updates.filterDufResults(goodQuery.cols.split(",").toList)
                result = updates.toString
              }

              Ok(result)
            }
          }
        }
      )
  }




  /**
   * adds a row to the queryResult
   * @param queryResult the queryResult to add the row to
   * @param row the raw row data from the database
   */
  def addRow(queryResult:QueryResult,row:Row): Unit = {
    val rawData = row.asMap
    var betterRow:Map[String, String] = Map()
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
   * this is the method that actually does the legwork for create or drop table.
   *
   * @param goodStatement the table in question
   * @return a httpresult that tells you whether it works or not.
   */
  private def createOrDropTable(goodStatement:SQLStatement): SimpleResult =
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
