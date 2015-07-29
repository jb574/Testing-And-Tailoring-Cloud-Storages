package Actors.Replicators

import Actors.Messages.{InformationRequest, Message}
import Actors.SystemActor
import akka.actor.ActorRef
import controllers.SettingsManager
import models.{BasicAvailStatsGenerator, QuerySet}
import models.SQLStatementHelper.MutableSQLStatement
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * actor that allows us to
 * get useful data on our basic availbility
 * @author Jack Davey
 * @version 16th July 2015
 */
class AvailibilityChecker(repOverseer:ActorRef,logger:ActorRef) extends SystemActor(logger)
{
  var outstandingwork = 0
  def checkAvailibilityTargets(): Unit =
  {
      if(outstandingwork == 0)
      {
        logger ! Message("all targets met")
      }
      else
      {
        logger ! Message(s"there were $outstandingwork requests" +
          s"still still inconsistent in the database")
        outstandingwork = 0
      }
  }

  def scheduleTargetCheck():Unit =
  {
    val time = SettingsManager.retrieveValue("avTarget")
    context.system.scheduler.scheduleOnce(time seconds)
    {
      checkAvailibilityTargets()
    }
  }



   def receive =
   {
     case work:QuerySet => outstandingwork += 1
     case (true,work:MutableSQLStatement)  => outstandingwork -=  1
        BasicAvailStatsGenerator.addSucsess
     case (false,work:MutableSQLStatement) =>
       failedQueries = work :: failedQueries
       BasicAvailStatsGenerator.AddFailure
     case InformationRequest =>
       sender ! failedQueries.toString()
   }

  var failedQueries:List[MutableSQLStatement] = List()

}


