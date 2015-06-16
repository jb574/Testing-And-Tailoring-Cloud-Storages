package Actors.Replicators

import Actors.SystemActor
import Actors.Messages._
import akka.actor.{Props, ActorRef}

import scala.collection.mutable.ArrayBuffer

/**
 * this class is responsible for  overseeing the actual eventual consistency
 * process
 * @author Jack Davey
 * @version 16th June 2015
 */
class ReplicationOverSeer(logger:ActorRef) extends SystemActor(logger)
{
  var servers:ArrayBuffer[ActorRef] = ArrayBuffer()

  override  def preStart() =
  {
      for(index <- 0 to 3)
      {
        val server = context.actorOf(Props(new ReplicationServer(logger,index)))
        servers.insert(index,server)
      }
      updateReferenceLists
  }



  def updateReferenceLists = servers.foreach((thing) => thing ! servers)

  def receive =
  {
    case _ => println("got message")
  }


}
