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
 * get useful data on our basic availability
 * @author Jack Davey
 * @version 16th July 2015
 * @param repOverseer the replication overseer that we send failed updates to
 *  @param logger the logging actor for monitoring purposes
 */
class AvailabilityChecker(repOverseer:ActorRef,logger:ActorRef) extends SystemActor(logger)
{
  var outstandingWork = 0

  /**
   * method that checks our availability targets
   * and logs the result
   */
  def checkAvailabilityTargets =
  {
      if(outstandingWork == 0)
      {
        logger ! Message("all targets met")
      }
      else
      {
        logger ! Message(s"there were $outstandingWork requests" +
          s"still still inconsistent in the database")
        outstandingWork = 0
      }
  }

  /**
   * schedules a new check on the system's
   * availability requirements 
   */
  def scheduleTargetCheck() =
  {
    val time = SettingsManager.retrieveValue("avTarget")
    context.system.scheduler.scheduleOnce(time seconds)
    {
      checkAvailabilityTargets
    }
  }


  /**
   * main receive block for this class
   * it logs the successes and failures after having received them from other
   * actors
   */
   def receive =
   {
     case work:QuerySet => outstandingWork += 1
     case (true,work:MutableSQLStatement)  => outstandingWork -=  1
        BasicAvailStatsGenerator.addSucsess
     case (false,work:MutableSQLStatement) =>
       failedQueries = work :: failedQueries
       BasicAvailStatsGenerator.AddFailure
     case InformationRequest =>
       sender ! failedQueries.toString()
   }

  var failedQueries:List[MutableSQLStatement] = List()

}


