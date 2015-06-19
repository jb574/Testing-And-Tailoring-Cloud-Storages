package models

import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json

/**
 * Created by jackdavey on 19/06/15.
 */
object DeleteStatementHelper {

  case class DeleteStatment(private val tables: List[String],OtherClauses:String, val where: Map[String, String]) extends MutableSQLStatement(tables)
  {

    override def getNewSQLStatement  =
    {
      val tableString = tables.mkString(",")
      val whereClause = produceStringOutput(where)
      s"delete from $tableString where $whereClause $OtherClauses ; "
    }
  }
  implicit val TableeWrites = Json.writes[DeleteStatment]
  implicit val TableReads = Json.reads[DeleteStatment]


}
