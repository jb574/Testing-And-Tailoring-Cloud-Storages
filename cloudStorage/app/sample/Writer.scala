package sample

import java.util.Random

import models.SelectStatementHelper.SelectStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment

/**
 * Created by jackdavey on 03/07/15.
 */
class Writer(db:DatabaseConnector)
{
  var firstResult = db.read()
  var expectedResults:List[Int]  = List(firstResult)

  def makeWrite():Unit =
  {
    val random = new Random()
    val age =  random.nextInt(100)
    expectedResults = age :: expectedResults
    println(s"writing $age  ")
    db.write(age)
  }

  def makeWriteAndRead():Boolean =
  {
    makeWrite()
    makeRead()
  }

  def makeRead(): Boolean =
  {

    val result = db.read()
    goodResponse(result)
  }


  /**
   * method to check to see whether
   * the result from an sql query
   * passed in as a string
   *  conforms to what has been prviusly
   *  requested of the database.
   * @param response the result string to check
   * @return a boolean indicating yes or no
   */
  def goodResponse(response:Int):Boolean =
  {
    !expectedResults.contains(response)
  }


}
