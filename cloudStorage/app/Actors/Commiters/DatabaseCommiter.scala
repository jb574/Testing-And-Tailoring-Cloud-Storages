package Actors.Commiters

import Actors.Messages._
import Actors.SystemActor
import akka.actor.ActorRef
import anorm._
import models.SQLStatementHelper.{MutableSQLStatement, SQLStatement}
import play.api.db.DB


/**
 * this actor is the one responsible for making
 * database operations fully consistent
 * it does not do any of the consistency
 * checks itself but it  sends any sql queries to the database
 * as it gets them.
 * @author Jack Davey
 * @version 16th June 2015
 */
class DatabaseCommiter(logger:ActorRef) extends SystemActor(logger)
{

  def receive =
  {
    case  DatabaseOperation(update:MutableSQLStatement) => applyDatabaseUpdate(update)
  }


  private def applyDatabaseUpdate(goodStatement:SQLStatement) =
  {
    try
    {
      DB.withConnection
      {
        implicit con =>
          val result = SQL(goodStatement.getNewSQLStatement).executeUpdate()
          logger !  Message(s"$result update sucsessfully in database")
      }
    }
    catch {
      case error: Exception =>  logger ! Message("bad request" + error.getMessage)
    }
  }

}
