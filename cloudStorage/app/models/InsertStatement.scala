package models

import models.SQLStatementHelper.MutableSQLStatement
import play.api.libs.json.Json

object InsertStatmentHelper
{
  case class InsertStatment(private val tables: List[String], private val values: Map[String, String]) extends MutableSQLStatement(tables)
  {

    override def getNewSQLStatement  =
    {
      val tableString = tables.mkString(",")
      var cols = "("
      var dataVals = "("
      for((name,value) <- values)
      {
        cols = cols + name + ","
        dataVals = dataVals + value + ","
      }
      cols = endSQLText(cols)
      dataVals = endSQLText((dataVals))
      s"insert into $tableString $cols values $dataVals;"
    }

    def endSQLText(text:String):String =
    {
      text.dropRight(1)
      text + ")"
    }
  }
  implicit val TableeWrites = Json.writes[InsertStatment]
  implicit val TableReads = Json.reads[InsertStatment]

}



