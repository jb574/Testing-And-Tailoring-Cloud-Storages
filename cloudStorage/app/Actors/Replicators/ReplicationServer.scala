package Actors.Replicators

import Actors.{Logger, SystemActor}
import akka.actor.ActorRef
 import scala.collection.mutable.ArrayBuffer

/**
 * this represents one replication server
 * @author Jack Davey
 * @version 16th June 2015
 */
class ReplicationServer(logger:ActorRef,id:Int) extends SystemActor(logger)
{
   private var otherServers:ArrayBuffer[ActorRef] = ArrayBuffer()

  def receive =
  {
    case serverList:ArrayBuffer[ActorRef] => otherServers = serverList
  }

}
