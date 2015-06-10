package models

import play.api.libs.json.Json

/**
 * wrapper around the main
 * SQLQuery class, mainly needed for things like JSON
 * encoding
 * @author Jack Davey
 * @version 8th June 2015
 */
object  SQLQuery
{


  /**
   * class that represents
   * an sql query,
   * this is a very simplistic model and probably
   * won't be in the final version
   * @author Jack Davey
   * @version 8th June 2015
   */
  case class SQLQuery(private val queryType: String, val queryContents: String) {


    /**
     * method that checks that
     * thsi sql query is the type
     * provided by the parameter
     * @param possibleType the type to check
     * @return as above
     */
    def isValidQueryType(possibleType: String): Boolean = {
      println(possibleType)
      println(queryType)
      println(queryContents)
      queryType.contains(possibleType) && queryContents.contains(possibleType)
    }

    /**
     * method to preform very simple
     * validation on newly created queries
     * @return true if thi quer is one of the four valid query types
     *         (select, update, insert or delete)
     */
    def isValidQuery: Boolean =
      isValidQueryType("select") || isValidQueryType("update")

    isValidQueryType("insert") || isValidQueryType("delete")


  }

  /**
   * simple add method to add a query
   * to what we've seen so far
   * @param query the query to add
   * @return a message that reports
   *         whether the operation was a sucsess
   */
  def addQuery(query:SQLQuery) =
  {
      queryList = query :: queryList
  }

  def JsonVer = Json.toJson(queryList)

  private var queryList = List( SQLQuery("select","select * from somwehere"))
   // syntactic sugar here so that our class can very easily read and write josn.
  implicit val SQLWrites = Json.writes[SQLQuery]
  implicit val SQLReads = Json.reads[SQLQuery]

}
