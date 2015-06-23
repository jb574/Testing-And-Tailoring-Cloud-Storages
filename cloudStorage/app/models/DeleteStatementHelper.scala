package models

import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json

/**
 * Created by jackdavey on 19/06/15.
 */
object DeleteStatementHelper {

  case class DeleteStatment(private val tables: List[String],OtherClauses:String, where: Map[String, String]) extends MutableSQLStatement(tables)
  {

    override def getNewSQLStatement  =
    {
      val tableString = tables.mkString(",")
      val whereClause = produceStringOutput(where)
      s"delete from $tableString where $whereClause $OtherClauses ; "
    }

    def this(tables:List[String],where:Map[String,String]) =
    {
      this(tables,"",where)
    }

  }



  implicit val TableeWrites = Json.writes[DeleteStatment]
  implicit val TableReads = Json.reads[DeleteStatment]


}
