package Actors

import akka.actor.ActorRef
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout._
/**
 * object to hold various utility
 * methods
 * @author Jack Davey
 * @version 12th August 2015
 */
object ActorUtils
{
  /**
   * sends a message to a server
   * and awaits the result
   * @param msg the message to send
   * @param server the server to send the message to
   * @tparam message the type of the message
   * @tparam result  the type of the expected result
   * @return the result that was actually returned
   */
  def sendMessage[message,result](msg:message,server:ActorRef):result =
  {
    implicit val timeout = Timeout(5 seconds)
    val res = server ? msg
    Await.result(res,5 seconds)
    res.value.get.get.asInstanceOf[result]
  }
}
