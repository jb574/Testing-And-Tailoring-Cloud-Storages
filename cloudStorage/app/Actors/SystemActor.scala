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

}
