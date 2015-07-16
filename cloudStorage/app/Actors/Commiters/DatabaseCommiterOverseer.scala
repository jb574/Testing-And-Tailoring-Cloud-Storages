package Actors.Commiters

import Actors.Messages.DatabaseOperation
import Actors.SystemActor
import akka.actor.{ActorRef, PoisonPill, Props}
import models.SQLStatementHelper
import models.SQLStatementHelper.MutableSQLStatement

import scala.collection.mutable.ArrayBuffer

/**
 * class that takes fully consistent requests from
 * the replication servers and writes them to the database
 * proper
 * @author Jack Davey
 * @version 16th June 2015
 * @param logger  a reference to the logger actor
 */
class DatabaseCommiterOverseer (logger:ActorRef) extends SystemActor(logger)
{
  var seenQueries:Set[MutableSQLStatement] = Set()

  /**
   * main receive block for this actor
   * if it gets a list of MutableSQL queries,
   * it sends them, one by one to the database
   * if it receives anything else, then it crashes the whole system
   */
   def receive =
   {
     case updates:List[MutableSQLStatement] => updates.map(query => transmitDataToDatabase(query))
     case msg  => error(getClass.toString,msg.getClass.toString)
   }


  /**
   * method to transmit data to database,
   * it checks that it hasnt already
   * seen the  update, if not then it creates
   * a child actor to send it to the database
   * @param update  the update to send to the database
   */
  private def transmitDataToDatabase(update: MutableSQLStatement): Unit =
  {
    if(!seenQueries.contains(update))
    {
      println("sending to database now")
      val slave = context.actorOf(Props(new DatabaseCommiter(logger)))
      slave ! update
      slave ! PoisonPill
      seenQueries = seenQueries + update
    }
  }
}
