package models
import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json

/**
 * Created by jackdavey on 16/06/15.
 */

object UpdateTableStatmentHelper
{
   case class UpdateTableStatment(private val tables: List[String],  val set: Map[String, String], val where: Map[String, String]) extends MutableSQLStatement(tables)
  {

    override def getNewSQLStatement  =
    {
      val tableString = tables.mkString(",")
      val  colSet = produceStringOutput(set)
      val whereClause = produceStringOutput(where)
      s"update $tableString set $colSet where $whereClause ; "
    }
  }
  implicit val TableeWrites = Json.writes[UpdateTableStatment]
  implicit val TableReads = Json.reads[UpdateTableStatment]

}


