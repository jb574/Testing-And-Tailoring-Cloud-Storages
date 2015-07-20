package Actors.Replicators

import Actors.SystemActor
import akka.actor.ActorRef
import models.QuerySet
import models.SQLStatementHelper.MutableSQLStatement


/**
 * actor that allows us to
 * get useful data on our basic availbility
 * @author Jack Davey
 * @version 16th July 2015
 */
class AvailibilityChecker(repOverseer:ActorRef,logger:ActorRef) extends SystemActor(logger)
{
  var outstandingwork = 0
   def receive =
   {
     case work:QuerySet => outstandingwork += 1
     case (true,work:QuerySet)  => outstandingwork -= work.queries.size
     case (false,work:MutableSQLStatement) =>   repOverseer ! work
   }
}


