package Actors.Replicators

import Actors.SystemActor
import akka.actor.ActorRef
import models.QuerySet
import scala.collection.mutable.ArrayBuffer

/**
 * Created by jackdavey on 18/06/15.
 */
class ReplicationMarshaller(logger:ActorRef,committer:ActorRef) extends SystemActor(logger)
{
    private var noSeen = 0;
   private var queries:ArrayBuffer[QuerySet]  = ArrayBuffer()

  def receive =
  {
    case list:ArrayBuffer[QuerySet] =>  queries = queries ++ list
      noSeen = noSeen + 1
      if(noSeen == 6)
      {
        queries.clear()
      }
      list.foreach(query => query.sendQuerries(committer))
  }
}
