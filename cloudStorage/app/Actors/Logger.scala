package Actors
import akka.actor._
import akka.pattern.AskSupport
import models.LogHelper
import Actors.Messages._
/**
 * this is the actor that
 * handles all  logging for the application
 * @author Jack Davey
 * @version 16th June 2014
 */
class Logger  extends Actor with AskSupport
{

  override def preStart(): Unit =
  {
    println("hello world")
    self ! Message("hello world")
  }

   def receive =
   {
     case Message(msg:String) => LogHelper.addLogEntry(msg)
     case _ =>
       throw new IllegalArgumentException("invalid message sent to the logger")
   }
}
