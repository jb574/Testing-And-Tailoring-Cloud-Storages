package Actors.Commiters

import Actors.Messages.DatabaseOperation
import Actors.SystemActor
import akka.actor.{ActorRef, PoisonPill, Props}
import models.SQLStatementHelper.MutableSQLStatement

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
   def receive =
   {
     case DatabaseOperation(update:MutableSQLStatement) =>
        val slave = context.actorOf(Props(new DatabaseCommiter(logger)))
        slave ! update
        slave ! PoisonPill
     case _ => throw new IllegalArgumentException("lnvalid message type")
   }
}
