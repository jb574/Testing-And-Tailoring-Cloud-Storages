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

  /**
   * code that is run as soon as the logger starts up
   */
  override def preStart(): Unit =
  {
    println("hello world")
    self ! Message("hello world")
  }

  /**
   * our recieve block, if we get a meessage
   * then we log it otherwise we just error
   * @return
   */
   def receive =
   {
     case Message(msg:String) => LogHelper.addLogEntry(msg)
     case thing:Any => throw new IllegalArgumentException("" +
     s"${getClass.toString} cannot handle messages of type ${thing.getClass.toString}")
   }
}
