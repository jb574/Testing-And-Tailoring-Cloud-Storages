package models

import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json


/**
 * object that allows for deletion of rows
 * @author Jack Davey
 *  @version 15th June 2015
 */
object DeleteStatementHelper {

  /**
   * actual class that allows for row deletion
   * @author Jack Davey
   *  @version 15th June 2015
   *  @param tables the tables from which to delete
   *  @param otherClauses any other clauses in the sql
   *  @param where the where clause of this statement
   */
  case class DeleteStatment(private val tables: List[String],OtherClauses:String, where: Map[String, String]) extends MutableSQLStatement(tables)
  {

    /**
     * @return a textual representation of the statement
     */
    override def getNewSQLStatement  =
    {
      val tableString = tables.mkString(",")
      val whereClause = produceStringOutput(where)
      s"delete from $tableString where $whereClause $OtherClauses ; "
    }

    /**
     * secondary constructor of this class
     * @param tables the tables from which to delete
     *  @param where the where clause of this statement
     */
    def this(tables:List[String],where:Map[String,String]) =
    {
      this(tables,"",where)
    }

  }



  implicit val TableeWrites = Json.writes[DeleteStatment]
  implicit val TableReads = Json.reads[DeleteStatment]


}
