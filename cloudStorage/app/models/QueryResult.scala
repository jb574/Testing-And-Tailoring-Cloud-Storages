package models

import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json
import scala.collection.Map
import models.UpdateTableStatmentHelper.UpdateTableStatment
import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
/**
 * this clas contains various helper methods
 * and functions to support
 * the QueryResultHelper class
 * see below for more details on this
 * @author Jack Davey
 * @version 22nd June 2015
 */
object QueryResultHelper 
{


  /**
   * this is the class that 
   * represents results for users, 
   * therefore this class is JSONParsable
   * @param results this allows for an existing set of results
   *                to be passed in if desired
   *@author Jack Davey
   * @version 22nd June 2015
   */
 case class QueryResult(private var tables:List[String],private var results:List[Map[String,String]])
  {
    var done:Boolean = false
    /**
     * auxilary constructor for this class
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
     * @return a signal to the web application on whther it is safe t
     *         return this query
     */
    def isDone:Boolean = done

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
     * @param otherTables the othertables to check
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
     * @param row  the row for which we are loooking
     * @return   a yes or no answer to the above question
     */
    def isWhereClauseValid(where:Map[String,String],row:Map[String,String]):Boolean =
    {
      val result = where.forall{case (key, value) => isValidCluase(row,key,value)}
      result
    }

    /**
     * find out if a partiular property of a row holds
     * for instance, when doing an SQL update, you might want to check whther
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
        for((col, value) <- row)
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
     * applies a delte to this resultSet
     * @param where a map representing the where clause for this delte set
     */
    def applyDelete(where:Map[String,String]) =
    {
      val applicableRows = findApplicableRows(where)
      results = results.filter(res => !applicableRows.contains(res))
    }

    /**
     * applies an update for this resultse
     * @param where   the where clause for this set
     * @param updates the set of updates to apply if this where clause holds 
     */
    def applyUpdate(where:Map[String,String],updates:Map[String,String]): Unit =
    {
      val applicableRows =  findApplicableRows(where)
      if(applicableRows.nonEmpty)
      {
        println("preforming update")
        results = results.map(row => checkRowForUpdates(row,where,updates))
        println(results.toString)
      }
    }

    /**
     * goes through a row and updates its
     * contets where nencessary
     * @param row   the  row in question
     * @param where  the where clause for the update
     * @param updates the set of updates to apply
     * @return the updated row
     */
    def checkRowForUpdates( row:Map[String,String],where:Map[String,String],updates:Map[String,String]):Map[String,String] =
    {
      if(isWhereClauseValid(where,row))
      {
        var resultRow:Map[String,String] = row
          updates.foreach{ case (key, value) => resultRow = updateValue(updates,resultRow,key) }
       return resultRow
      }
      row
    }

    def findApplicableRows(where: Map[String, String]):List[Map[String,String]] =
    {
      val res = results.filter(row => isWhereClauseValid(where, row))
      res
    }

    /**
     * method to add a new row to the queryResult
     * @param row
     */
    def addRow(row:Map[String,String]) =
      results = row :: results

    def this(other:QueryResult) =
    {
      this(other.tables,other.results)
    }
    
    
  }




}