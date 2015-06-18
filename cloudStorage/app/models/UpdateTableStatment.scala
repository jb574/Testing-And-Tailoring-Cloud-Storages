package models
import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json

/**
 * Created by jackdavey on 16/06/15.
 */

object UpdateTableStatmentHelper
{
   case class UpdateTableStatment(private val tables:List[String], private val set:List[String],val where:List[String]) extends MutableSQLStatement(tables)
  {

    override def getNewSQLStatement  =
    {
      val tableString = tables.mkString(",")
      val  colSet = set.mkString(",")
      val whereClause = where.mkString(",")
      s"update $tableString set $colSet where $whereClause ; "
    }
  }
  implicit val TableeWrites = Json.writes[UpdateTableStatment]
  implicit val TableReads = Json.reads[UpdateTableStatment]

}


