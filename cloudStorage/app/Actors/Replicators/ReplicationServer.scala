package Actors.Replicators

import Actors.Messages.{MakeConsistent, TestMessage, Message}
import Actors.SystemActor
import akka.actor.ActorRef
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
   private var otherServers:ArrayBuffer[ActorRef] = ArrayBuffer()
   private var inconsistentUpdates:List[QuerySet] = List()
   private var masterList:List[String]  = List()
   private var noSeen = 0
  def respondToTestMessage =
  {
    println(" message received by server " + id)
    logger ! Message(" message recieved by server $id")
  }


  def processNewQuery(update:MutableSQLStatement) =
  {
    respondToTestMessage
    if(!inconsistentUpdates.exists((clockSeq) => clockSeq.addNewQuery(update,id)))
    {
       inconsistentUpdates =new QuerySet(update,id) :: inconsistentUpdates
       println(inconsistentUpdates.size)
    }
  }

   private def distributeUpdates(): Unit =
  {
    for(index <- 0 to 2)
    {
       if(index != id)
       {
         otherServers(index) ! inconsistentUpdates
       }
    }
  }

 def mergeOneQuery(first:QuerySet,second:QuerySet, mergedQueries:ArrayBuffer[QuerySet]): Unit =
 {
   val resultQuery =  new QuerySet(second)
   if(resultQuery.canBeMeged(first))
   {
     println("merging now")
     resultQuery.mergeQuerySets(first)
     mergedQueries.append(resultQuery)
   }
 }

 def mergeOneExternalQuery(first:QuerySet,mergedQueries:ArrayBuffer[QuerySet]): Unit =
 {
   inconsistentUpdates.foreach((query) => mergeOneQuery(first,query,mergedQueries))
 }

  def receive =
  {

    case update:MutableSQLStatement => processNewQuery(update)
    case TestMessage => respondToTestMessage
    case serverList:ArrayBuffer[ActorRef] => otherServers = serverList
    case MakeConsistent => distributeUpdates()
    case foreignQueries:List[QuerySet] =>  makeConsistent(foreignQueries)
  }

  def makeConsistent(foreignQUeries: List[QuerySet]): Unit = {
    noSeen = noSeen + 1
    println("im cool   ")
    var resSet: ArrayBuffer[QuerySet] = ArrayBuffer()
    if(!foreignQUeries.isEmpty)
    {
      foreignQUeries.foreach(((query) => mergeOneExternalQuery(query, resSet)))
      println("numbe rof queries is" + resSet.size)
      resSet.foreach((set) => println(set.toString))
      replicationMarshaller ! resSet
    }
    if(noSeen == 2)
    {
      inconsistentUpdates = List()
    }
  }
}
