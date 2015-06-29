package Actors.Replicators

import Actors.Messages.{MakeConsistent, TestMessage, Message}
import Actors.SystemActor
import akka.actor.ActorRef
import models.QueryResultHelper.QueryResult
import models.QuerySet
import models.SQLStatementHelper.MutableSQLStatement
import java.time.LocalDate
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap

/**
 * this represents one replication server
 * @author Jack Davey
 * @version 16th June 2015
 */
class ReplicationServer(logger:ActorRef,id:Int,replicationMarshaller:ActorRef) extends SystemActor(logger)
{
    var otherServers:ArrayBuffer[ActorRef] = ArrayBuffer()
    var inconsistentUpdates:List[QuerySet] = List()
   var masterList:List[String]  = List()
    var noSeen = 0

  /**
   *method that announces that a message has been recieved
   * by this server
   */
  private def respondToTestMessage =
  {
    println(" message received by server " + id)
    logger ! Message(" message recieved by server $id")
  }


  /**
   * method that processes new updates upon arrival at the
   * replication server
   * @param update  the new update in question
   */
  private def processNewQuery(update:MutableSQLStatement) =
  {
    respondToTestMessage
    if(!inconsistentUpdates.exists((clockSeq) => clockSeq.addNewQuery(update,id)))
    {
       inconsistentUpdates =new QuerySet(update,id) :: inconsistentUpdates
       println(inconsistentUpdates.size)
    }
  }

  /**
   * method to send all inconsistent updates to all other
   * replication severs, to check that
   * they haven't seen anything mor erecent
   */
  private def distributeUpdates =
  {
    for(index <- 0 to 2)
    {
       if(index != id)
       {
         otherServers(index) ! inconsistentUpdates
       }
    }
  }

  /**
   * method to merge two querysets together if they both go together,
   * one should go before the other
   * @param first  the first queryset
   * @param second the second queryset
   * @param mergedQueries an arraybuffer containing the results
   *                      to send to the database
   */
 private def mergeOneQuery(first:QuerySet,second:QuerySet, mergedQueries:ArrayBuffer[QuerySet]): Unit =
 {
   val resultQuery =  new QuerySet(second)
   if(resultQuery.canBeMeged(first))
   {
     println("merging now")
     resultQuery.mergeQuerySets(first)
     mergedQueries.append(resultQuery)
   }
 }

  /**
   * merge an extenral query in from the rest of this system
   * @param first the query to merge
   * @param mergedQueries  an arraybuffer to collec tthe results to send to the database
   */
 private def mergeOneExternalQuery(first:QuerySet,mergedQueries:ArrayBuffer[QuerySet]): Unit =
 {
   inconsistentUpdates.foreach((query) => mergeOneQuery(first,query,mergedQueries))
 }

  /**
   * this is the recieve block for this actor
   * if a MutableSQL query arrives
   * then we processe it, we just announce testmesages
   * if we get a severlist then we eplace our existing
   * list with it. a makeConsistent request causes us to
   * distribute all our pieces of  data to all other requests. if we recieve
   * such a list of queries, then we  maeke it consistent and send the results onto the database.
   */
  def receive =
  {

    case update:MutableSQLStatement => processNewQuery(update)
    case TestMessage => respondToTestMessage
    case serverList:ArrayBuffer[ActorRef] => otherServers = serverList
    case MakeConsistent => distributeUpdates
    case foreignQueries:List[QuerySet] =>  makeConsistent(foreignQueries)
    case queryResult:QueryResult => inconsistentUpdates.foreach(update => update.executeQuery(queryResult))
      println("all done")
      queryResult.markComplete
  }

  /**
   * this is the main code that makes everything consistent
   * @param foreignQUeries the list of queries we recieved from all other replicas
   */
  def makeConsistent(foreignQUeries: List[QuerySet]): Unit =
  {
    noSeen = noSeen + 1
    println("im cool   ")
    var resSet: ArrayBuffer[QuerySet] = ArrayBuffer()
    if(!foreignQUeries.isEmpty)
    {
      if(inconsistentUpdates.isEmpty)
      {
        resSet = resSet ++ foreignQUeries
        println("all empty so sending everything straight through")
        println("this had " + foreignQUeries.size + " queries")
        replicationMarshaller ! resSet
        resSet.foreach((set) => println(set.toString))
      }
      else
      {
        foreignQUeries.foreach(query => mergeOneExternalQuery(query, resSet))
        println("numbe rof queries is" + resSet.size)
        resSet.foreach((set) => println(set.toString))
        replicationMarshaller ! resSet
      }
    }
    if(noSeen == 2)
    {
      inconsistentUpdates = List()
    }
  }
}
