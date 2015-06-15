package models


object SQLStatement
{
  private var lastID = 0

  def assignNewId:Long =
  {
    val newId = lastID
    lastID = lastID + 1
    newId
  }

  /**
   * this is the base class that all sql statements use
   * @author Jack Davey
   * @version 13th June 2014
   * @param tables the names of the database tables involved.
   */
  abstract class SQLStatement(private val tables: List[String])
  {
      private var id = assignNewId
      def getNewSQLStatement:String

  }

  /**
   * this is the superclass
   * for all sql operations  that
   * fall under the eventual consistency rules
   * @author Jack Davey
   * @version  15th June 2015
   * @param tables  the anes of the sql table that we want to change
   */
  abstract class MutableSQLStatement(private val tables:List[String]) extends SQLStatement(tables)
  {
    def getNewSQLStatement:String
  }

}