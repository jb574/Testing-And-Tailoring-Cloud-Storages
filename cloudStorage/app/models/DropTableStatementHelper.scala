package models

import play.api.libs.json.Json


/**
 * class to support dropping of tables through the application
 * @author Jack Davey
 * @version 15th June 2015
 */
object DropTableStatementHelper
{
  /**
   * this is the actual class itself
   * @author Jack Davey
   * @version 15th June 2015
   * @param tables the names of the database tables involved.
   */
  case class DropTableStatement(tables:List[String]) extends models.SQLStatementHelper.SQLStatement(tables) {
    if (tables.length > 1) {
      throw new IllegalArgumentException("drop table statements should" +
        "only be applied to one table")
    }

    /**
     * @return the textual representation of this sql query so that it can be run
     *         on the database
     */
    override def getNewSQLStatement: String = {
      val name = tables.head
      s"drop table $name;"
    }
  }

  implicit val TableeWrites = Json.writes[DropTableStatement]
  implicit val TableReads = Json.reads[DropTableStatement]
}
