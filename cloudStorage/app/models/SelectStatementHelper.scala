package models
import models.SQLStatementHelper._
import play.api.libs.json.Json

/**
 * class for representing select statements
 * @author Jack Davey
 * @version 22nd June 2015
 */
object SelectStatementHelper
{
  case class SelectStatement(tables:List[String],cols:String,restOfQuery:String) extends SQLStatement(tables)
  {
    override def getNewSQLStatement =
    {
      val tableString:String = tables.mkString(",")
      s"select $cols from $tableString $restOfQuery; "
    }

    def retrieveTableInfo():List[String] =
    {
      tables
    }
  }
  implicit val TableeWrites = Json.writes[SelectStatement]
  implicit val TableReads = Json.reads[SelectStatement]

}
