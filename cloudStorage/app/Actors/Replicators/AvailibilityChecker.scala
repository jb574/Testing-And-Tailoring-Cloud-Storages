package Actors.Replicators

import Actors.SystemActor
import akka.actor.ActorRef
import models.QuerySet


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
     case (true,work:QuerySet ) => outstandingwork -= 1
     case (false,work:QuerySet) =>   repOverseer ! work
   }
}
