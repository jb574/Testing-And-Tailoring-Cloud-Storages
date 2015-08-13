package Actors.Replicators

import Actors.SystemActor
import akka.actor.ActorRef
import controllers.SettingsManager
import models.{BasicAvailStatsGenerator, QuerySet}
import scala.collection.mutable.ArrayBuffer


/**
 * class that intercepts updates
 * from the replication servers to send to the
 * database, once it sees a certain number of queries, it flushes everything and starts
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


  /**
   * receive block for this actor
   * all it looks for are lists of QuerySets, which it sends onto the 
   * database
   */
  def receive =
  {
    case list:List[QuerySet] => sendUpdatesToDatabase(list)
    case msg  => error(getClass.toString,msg.getClass.toString)
  }

  /**
   * method to end a list of queries to the database
   * @param list   the list to send 
   */
  def sendUpdatesToDatabase(list: List[QuerySet]) =
  {
    list.foreach(query => queries.append(query))
    noSeen = noSeen + 1
    println(noSeen)
    if (noSeen == SettingsManager.retrieveValue("primServers"))
    {
      println("clear")
      while(queries.nonEmpty)
      {
        var first = true
        var earliest = new QuerySet()
        queries.foreach((query) => if(query.dateCreated.isBefore(earliest.dateCreated))
        earliest = query)
        earliest.sendQueries(committer)
        queries = queries - earliest
      }
      noSeen = 0
      BasicAvailStatsGenerator.recordStats()
    }

  }
}
