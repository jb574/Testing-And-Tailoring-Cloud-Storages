package models

import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json
import models.UpdateTableStatmentHelper.UpdateTableStatment
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
/**
 * this class contains various helper methods
 * and functions to support
 * the QueryResultHelper class
 * see below for more details on this
 * @author Jack Davey
 * @version 22nd June 2015
 */
object QueryResultHelper 
{


  /**
   * class to represent a row in the result
   * set
   * @param columns the columns of the row
   * @author Jack Davey
   * @version 13th August 2015
   */
  case class Row(var columns:Map[String,String])
  implicit val rowWrites = Json.writes[Row]
  implicit val rableReads = Json.reads[Row]


  /**
   * this is the class that 
   * represents results for users, 
   * therefore this class is JSONParsable
   * @param results this allows for an existing set of results
   *                to be passed in if desired
   *@author Jack Davey
   * @version 22nd June 2015
   */
 case class QueryResult(private var tables:List[String],private var results:List[Row])
  {
    var done:Boolean = false

    /**
     * @return the number of rows in
     *         the result set
     */
    def size:Int = results.size

    /**
     * @return get teh column headers
     *         as a set of strings
     */
    def headers:Set[String] = results.head.columns.keySet

    /**
     * @param index the index of a particular row
     * @return the row at position index specified
     *         above
     */
    def getRow(index:Int):Row =
    {
      var counter = 0
      for(row:Row <- results)
      {
        if(counter == index)
        {
          return row
        }
        counter = counter + 1
      }
      throw new IndexOutOfBoundsException(" that row doesn't exist")
    }

    /**
     * retruns a field
     * fromt he row at the index
     * withthe column name name
     * @param index  the index  of the row to look for
     * @param name the name of the field ot look for
     * @return as above
     */
    def getField(index:Int, name:String):String =
    {
      val row = getRow(index)
      if(row.columns.contains(name))
      {
        row.columns(name)
      }
      else
      {
        throw new IllegalArgumentException("no column with that name")
      }
    }


    /**
     * auxiliary constructor for this class
     * it allows for the creation of an empty
     * QueryResult without any results previously being
     * added
     */
     def this()
     {
       this(List(),List())
     }


    /**
     * mark this query as complete, so it can
     * be returned to the user
     */
    def markComplete = done = true

    /**
     * @return a signal to the web application on whether it is safe to
     *         return this query
     */
    def isDone:Boolean = done

   def filterDufResults(cols:List[String]) =
   {
     if(!cols.contains("*"))
     {
       results = results.map((row) =>
         Row(row.columns.filter{case (key,value) => cols.contains(key)})
       )
     }
   }




    /**
     * applies the effects of a specific mutable sql
     * update on this set of results
     * @param mutableSQLStatement the statement to apply
     */
    def applySQLUpdate(mutableSQLStatement: MutableSQLStatement): Unit =
    {
      mutableSQLStatement match
      {
        case update:UpdateTableStatment => applyUpdate(update.where,update.set)
        case insert:InsertStatment => addRow(insert.values)
        case delete:DeleteStatment => applyDelete(delete.where)
      }
    }

    /**
     * find out of we are talking about the same
     * set of tables
     * @param otherTables the other tables to check
     * @return as above
     */
    def talkingAboutSameTable(otherTables:List[String]): Boolean=
      tables.equals(otherTables)


    /**
     * secondary constructor for this
     * class that just allows you to pass 
     * the tables alone
     * @param tables as above
     */
   def this(tables:List[String]) =
   {
     this(tables,List())
   }

    /**
     * find out if a specific where clause is valid
     * @param where a map representing the where clause
     * @param row  the row for which we are looking
     * @return   a yes or no answer to the above question
     */
    def isWhereClauseValid(where: Map[String, String], row: Row):Boolean =
    {
      val result = where.forall{case (key, value) => isValidCluase(row.columns,key,value)}
      result
    }

    /**
     * find out if a particular property of a row holds
     * for instance, when doing an SQL update, you might want to check whether
     * the name column is equal to Jack
     * @param row  the row to test on
     * @param key  the name of the column we want to test
     * @param value the value that cell should hold 
     * @return  a yes or no answer to the above question
     */
     def isValidCluase(row:Map[String,String],key:String, value:String):Boolean =
    {
      if(row.contains(key))
      {
        var newValue = value.replaceAll("'","")
        newValue = newValue.trim
       val res = newValue.equals(row(key))
         return res
      }
      false
    }

    /**
     * @return a string representation of this object
     */
    override  def toString =
    {
      var result = ""
      results.foreach((row) =>
        for((col, value) <- row.columns)
        {
          result = result  + (col + " " + value + " ")
        }
      )
      result
    }


    /**
     * updates a particular 
     * column in a row
     * @param updates a map which details all the updates
     * @param row the row in question
     * @param key the key by which to update
     * @return   the newly updated row 
     */
    def updateValue(updates:Map[String,String], row:Map[String,String],key:String):Map[String,String] =
    {
      if(row.contains(key))
      {
         val value = updates(key)
        return row + (key -> value)
      }
      row
    }

    /**
     * applies a delete to this resultSet
     * @param where a map representing the where clause for this delete set
     */
    def applyDelete(where:Map[String,String]) =
    {
      val applicableRows = findApplicableRows(where)
      results = results.filter(res => !applicableRows.contains(res))
    }

    /**
     * applies an update for this results
     * @param where   the where clause for this set
     * @param updates the set of updates to apply if this where clause holds 
     */
    def applyUpdate(where:Map[String,String],updates:Map[String,String]): Unit =
    {
      val applicableRows =  findApplicableRows(where)
       println(s"number of applicable rows is ${applicableRows.length}")
      if(applicableRows.nonEmpty)
      {
        println("preforming update")
        results = results.map(row => checkRowForUpdates(row, where, updates))
        println(results.toString)
      }
    }

    /**
     * goes through a row and updates its
     * contents where necessary
     * @param row   the row in question
     * @param where  the where clause for the update
     * @param updates the set of updates to apply
     * @return the updated row
     */
    def checkRowForUpdates(row: Row, where: Map[String, String], updates: Map[String, String]):Row =
    {
      if(isWhereClauseValid(where, row))
      {
        var resultRow:Map[String,String] = row.columns
          updates.foreach{ case (key, value) => resultRow = updateValue(updates,resultRow,key) }
       return Row(resultRow)
      }
      row
    }

    def findApplicableRows(where: Map[String, String]):List[Row] =
    {
      val res = results.filter(row => isWhereClauseValid(where, row))
      res
    }

    /**
     * method to add a new row to the queryResult
     * @param row
     */
    def addRow(row:Map[String,String]) =
      results = Row(row) :: results

    def this(other:QueryResult) =
    {
      this(other.tables,other.results)
    }
    
    
  }

  implicit val resultrites = Json.writes[QueryResult]
  implicit val resultReads = Json.reads[QueryResult]


}