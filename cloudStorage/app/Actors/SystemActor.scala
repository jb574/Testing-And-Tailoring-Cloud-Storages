package Actors
import akka.actor.{ActorRef, Actor}


/**
 *all of the actors that exist
 * in my system extend this
 * so that they all have a reference to the logger
 * actor
 * @author Jack Davey
 * @version 16th June 2015
 * @param logger  a reference to the logger actor
 */
 abstract class SystemActor(logger:ActorRef)  extends Actor
{
 /**
  * method that raises an error if
  * we get a message we can't handle
  * @param actor the  actor the actor that recieved the message
  * @param message the message that was sent
  */
 def error(actor:String,message:String) =
 {
  throw new IllegalArgumentException("" +
    s"$actor cannot handle messages of type $message")
 }
}
