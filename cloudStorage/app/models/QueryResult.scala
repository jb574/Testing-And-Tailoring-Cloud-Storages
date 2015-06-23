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

    def markComplete = done = true;

    def isDone:Boolean = done

    def applySQLUpdate(mutableSQLStatement: MutableSQLStatement): Unit =
    {
      mutableSQLStatement match
      {
        case update:UpdateTableStatment => applyUpdae(update.where,update.set)
        case insert:InsertStatment => addRow(insert.values)
        case delete:DeleteStatment => applyDelete(delete.where)
      }
    }

    def talkingAboutSameTable(otherTables:List[String]): Boolean=
      tables.equals(otherTables)


   def this(tables:List[String]) =
   {
     this(tables,List())
   }

    def isWhereClauseValid(where:Map[String,String],row:Map[String,String]):Boolean =
    {
       !where.exists{case (key, value) => !isValidCluase(row,key,value)}
    }

     def isValidCluase(row:Map[String,String],key:String, value:String):Boolean =
    {
      if(row.contains(key))
      {
        value.equals(row(key))
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



    def updateValue(updates:Map[String,String], row:Map[String,String],key:String):Map[String,String] =
    {
      if(row.contains(key))
      {
         var value = updates(key)
         row + (key -> value)
      }
      row
    }

    def applyDelete(where:Map[String,String]) =
    {
      val applicableRows = findApplicableRows(where)
      results = results.filter(res => !applicableRows.contains(res))
    }

    def applyUpdae(where:Map[String,String],updates:Map[String,String]): Unit =
    {
      val applicableRows =  findApplicableRows(where)
      updates.foreach{case (key,value) => applicableRows.foreach(row => updateValue(updates,row,key))}
    }

    def findApplicableRows(where: Map[String, String]):List[Map[String,String]] =
    {
       results.filter(row => isWhereClauseValid(where, row))
    }

    /**
     * method to add a new row to the queryResult
     * @param row
     */
    def addRow(row:Map[String,String]) =
      results = row :: results
    
    
    
  }




}