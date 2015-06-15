package models

import play.api.libs.json.Json

/**
 * class to allow represntation of SQL
 * create table statements
 * @author Jack Davey
 * @version 15th June 2015
 */
  object CreateTableStatementHelpers
{

  /**
   * this is thae actual class itself
   * @author Jack Davey
   * @version 15th June 2015
   * @param tables the names of the database tables involved.
   * @param columns the columns of the new table
   */
  case class CreateTableStatement(tables:List[String],columns:List[String], private val  otherInfo:String) extends models.SQLStatementHelper.SQLStatement(tables)
  {
    if(tables.length > 1)
    {
      throw new IllegalArgumentException("create table statements dhould" +
        "only be applied to one table")
    }

    /**
     * @return the textual representation of this sql query so that it can be run
     *         on the database
     */
    override   def getNewSQLStatement:String =
    {
      val name = tables.head
      val column = columns.mkString(",")
      val result =  s"create table $name (" +
        s"id integer not null unique, $column"
      if(!otherInfo.isEmpty)
      {
        result + s", $otherInfo) engine innodb;"
      }
      else
      {
        result + ") engine innodb;"
      }
    }
  }
  implicit val TableeWrites = Json.writes[CreateTableStatement]
  implicit val TableReads = Json.reads[CreateTableStatement]

}
