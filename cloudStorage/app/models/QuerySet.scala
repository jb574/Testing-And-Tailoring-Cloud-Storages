package models
import java.time.LocalDateTime
import models.QueryResultHelper.QueryResult
import models.SQLStatementHelper.MutableSQLStatement
import scala.collection.mutable.Map
import akka.actor.ActorRef
/**
 * this class represents a set of SQL operations that operate on the same
 * data
 * @author Jack Davey
 * @version 16th June 2015
 */
class QuerySet( var vectorClocks:Map[Int,LocalDateTime],  var queries:List[MutableSQLStatement],
                var tableNames:List[String])
{
  val dateCreated = LocalDateTime.now()

  /**
   * method to find out if two updates are relevan
   * @param update the update to check
   * @return  a boolean indicating whether the update
   *          and this query set work on the same tables.
   */
  def isUpdateRelavant(update:MutableSQLStatement):Boolean  =
  {
     update.affectSameTable(tableNames)
  }
  /**
   * this does a similar job to the method above
   * except it compares two QuerySet objects
   * as opposed to a Queryset and an update
   * @param other the QuerySet to check
   * @return  a boolean indicating whether the update
   *          and this query set work on the same tables.
   */

  def dealQithSameeData(other:QuerySet):Boolean =
  {
    other.tableNames.equals(tableNames)
  }

  /**
   * copy constructor for this class
   * @param querySet  the queryset to copy
   */
  def this(querySet: QuerySet)
  {
    this(querySet.vectorClocks,querySet.queries,querySet.tableNames)
  }


  /**
   * runs all the sql queries stored
   * in this  queryset against the
   * resultset parameter
   * @param QueryResult the resultset in question
   */
  def executeQuery(QueryResult:QueryResult) =
  {
    if(QueryResult.talkingAboutSameTable(tableNames))
    {
      queries.foreach(query => QueryResult.applySQLUpdate(query))
    }
  }

  /**
   * sends the list of queries for this
   * actor to the database committer
   * @param committer the comitter to send to
   */
  def sendQuerries(committer:ActorRef) = committer ! queries

  /**
   * method to check to see if two query sets
   * can be merged using a variation of Amazons stategy
   * for comparing vector Clocks (we use dates
   *  rather than sequence numbers)
   * @param other  the other queryset to check
   * @return   a yes or no answer to the above question
   */
   def canBeMeged(other:QuerySet):Boolean  =
   {
     var otherVectorClocks = other.vectorClocks
     val (id, date) = vectorClocks.head
     var maxDate = date
     for((id,newDate) <- vectorClocks)
     {
       if(newDate.isAfter(maxDate))
       {
         maxDate = newDate
       }
     }
     for((id, newDate) <- otherVectorClocks)
     {
       if(newDate.isAfter(maxDate))
       {
           false
       }
     }
     true
   }


  /**
   * method to merge another queryset into this
   * one
   * @param setToMerge  the other queryset to merge
   */
  def mergeQuerySets(setToMerge:QuerySet): Unit =
  {
    vectorClocks = vectorClocks ++ setToMerge.vectorClocks
    queries = queries ::: setToMerge.queries
  }


  /**
   * adds new tables to the list of tables that we are working
   * on
   * @param update  the sql query whose tables we want to add
   */
  def addNewTables(update:MutableSQLStatement) =
  {
    val tables:List[String] = update.retrietables
    var missingTables:List[String] = tables.filter((name) => !tableNames.contains(name))
    tableNames = tableNames ::: missingTables
  }


  /**
   * constructor that allows you to create
   * a new vecot rclock from the serverID
   * and sql update
   * @param mutableSQLStatement  the sql update
   * @param serverId  the server id
   */
  def this(mutableSQLStatement: MutableSQLStatement,serverId:Int)
  {
    this(Map((serverId,LocalDateTime.now())), List(mutableSQLStatement),mutableSQLStatement.retrietables())
  }

  /**
   *  adds anew query to this queryset
   * @param update the queyr in questin
   * @param serverID  the server id in question
   * @return  a boolean indicating sucsess or failure
   */
 def addNewQuery(update:MutableSQLStatement,serverID:Int):Boolean =
 {
   if(isUpdateRelavant(update))
   {
     addNewTables(update)
     val pair = (serverID,LocalDateTime.now())
     vectorClocks =  vectorClocks + pair
     queries  = update :: queries
     true
   }
   else 
   {
     false  
   }
 }

  /**
   * class that allows for the creation of new vector clocks
   * from existing ones
   * @param newClock the new vector clock to add
   * @param newQuery the new query to add
   * @return a new query set witht he new data added
   */
  def createNewQuerySet(newClock:(Int,LocalDateTime),newQuery:MutableSQLStatement):QuerySet =
  {
    val newClocks = vectorClocks + newClock
    val newQueries = newQuery :: queries
    new QuerySet(newClocks,newQueries,List())
  }


  override def toString: String =
  {
    var result = ""
    for(query <- queries)
    {
       result = result + query.getNewSQLStatement
    }
    result
  }

}
