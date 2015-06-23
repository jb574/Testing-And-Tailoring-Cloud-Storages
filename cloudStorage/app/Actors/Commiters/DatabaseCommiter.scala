package Actors.Commiters

import Actors.Messages._
import Actors.SystemActor
import akka.actor.ActorRef
import anorm._
import models.SQLStatementHelper.{MutableSQLStatement, SQLStatement}
import play.api.db.DB
import play.api.Play.current


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
  /**
   * recieve block for this actor
   * if a MutableSQLStatement is recieved,
   * it commits it to the database
   */
  def receive =
  {
    case  update:MutableSQLStatement    => applyDatabaseUpdate(update)
  }

  /**
   * method that applies fully consistent
   * updates to the database
   * @param goodStatement   the database update to commit
   */
  private def applyDatabaseUpdate(goodStatement:MutableSQLStatement) =
  {
    println(goodStatement.getNewSQLStatement)
    try
    {
      DB.withConnection
      {
        implicit con =>
          val result = SQL(goodStatement.getNewSQLStatement).executeUpdate()
          logger !  Message(s"$result update sucsessfully in database")
      }
    }
    catch
      {
      case error: Exception =>  logger ! Message("bad request" + error.getMessage)
    }
  }

}
