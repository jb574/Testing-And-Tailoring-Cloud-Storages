package Actors.Replicators

import Actors.Messages._
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



  /**
   * main receive block for this class
   * it logs the successes and failures after having received them from other
   * actors
   */
   def receive =
   {
     case status:Update =>
       if(status.ok)
       {
         println("good message")
         BasicAvailStatsGenerator.addSucsess(status.update)
       }
       else
       {
         println("bad message")
         BasicAvailStatsGenerator.AddFailure
       }
   }

}


