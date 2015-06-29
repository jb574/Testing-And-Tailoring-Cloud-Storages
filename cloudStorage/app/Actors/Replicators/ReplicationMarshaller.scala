package Actors.Replicators

import Actors.SystemActor
import akka.actor.ActorRef
import models.QuerySet
import scala.collection.mutable.ArrayBuffer


/**
 * class that intercepts updates
 * from the replication servers to send ot the
 * database,  once it sees a certian number of queires, it flushes everything and starts
 * over
 * @author Jack Davey
 * @version 17th June 2015
 * @param logger  a reference to the logger actor
 * @param committer the actor responsible for committing everything to the
 *                  database
 */
class ReplicationMarshaller(logger:ActorRef,committer:ActorRef) extends SystemActor(logger)
{
     var noSeen = 0;
    var queries:ArrayBuffer[QuerySet]  = ArrayBuffer()

  def receive =
  {
    case list:ArrayBuffer[QuerySet] =>  queries = queries ++ list
      noSeen = noSeen + 1
      println(noSeen)
      if(noSeen == 6)
      {
        println("clear")
        queries.clear()
        noSeen = 0
      }
      list.foreach(query => query.sendQuerries(committer))
  }
}
