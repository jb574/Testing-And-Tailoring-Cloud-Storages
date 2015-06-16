package models
import java.util.Date
  import models.SQLStatementHelper.MutableSQLStatement

/**
 * this class represents a set of SQL operations that operate on the same
 * data
 * @author Jack Davey
 * @version 16th June 2015
 */
class QuerySet(vectorClocks:List[(Int,Date)],queries:List[MutableSQLStatement])
{


  /**
   * class that allows for the creation of new vector clocks
   * from existing ones
   * @param newClock the new vector clock to add
   * @param newQuery the new query to add
   * @return a new query set witht he new data added
   */
  def createNewQuerySet(newClock:(Int,Date),newQuery:MutableSQLStatement):QuerySet =
  {
    val newClocks = newClock :: vectorClocks
    val newQueries = newQuery :: queries
    new QuerySet(newClocks,newQueries)
  }


}
