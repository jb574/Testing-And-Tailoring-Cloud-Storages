package models
import java.time.LocalDateTime
import models.SQLStatementHelper.MutableSQLStatement
import scala.collection.mutable.Map
import akka.actor.ActorRef
/**
 * this class represents a set of SQL operations that operate on the same
 * data
 * @author Jack Davey
 * @version 16th June 2015
 */
class QuerySet(private var vectorClocks:Map[Int,LocalDateTime], private var queries:List[MutableSQLStatement],
             private   var tableNames:List[String])
{
  val dateCreated = LocalDateTime.now()
  def isUpdateRelavant(update:MutableSQLStatement):Boolean  =
  {
     update.affectSameTable(tableNames)
  }
  def dealQithSameeData(other:QuerySet):Boolean =
  {
    other.tableNames.equals(tableNames)
  }

  def this(querySet: QuerySet)
  {
    this(querySet.vectorClocks,querySet.queries,querySet.tableNames)
  }


  def executeQuery(QueryResult:QueryResultHelper.QueryResult) =
  {
    if(QueryResult.talkingAboutSameTable(tableNames))
    {
      queries.foreach(query => QueryResult.applySQLUpdate(query))
    }
  }

  def sendQuerries(committer:ActorRef) = committer ! queries

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


  def mergeQuerySets(setToMerge:QuerySet): Unit =
  {
    vectorClocks = vectorClocks ++ setToMerge.vectorClocks
    queries = queries ::: setToMerge.queries
  }

  def addNewTables(update:MutableSQLStatement) =
  {
    val tables:List[String] = update.retrietables
    var missingTables:List[String] = tables.filter((name) => !tableNames.contains(name))
    tableNames = tableNames ::: missingTables
  }


  def this(mutableSQLStatement: MutableSQLStatement,serverId:Int)
  {
    this(Map((serverId,LocalDateTime.now())), List(mutableSQLStatement),mutableSQLStatement.retrietables())
  }

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
