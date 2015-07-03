package sample

import java.util.Random

import models.SelectStatementHelper.SelectStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment

/**
 * Created by jackdavey on 03/07/15.
 */
class Writer
{
  var expectedResults:List[Int]  = List()
  val query = new SelectStatement(List("test"),"*","")
  var firstResult = WebServiceUtils.executeSelect(query)
  def makeWrite():Unit =
  {
    val random = new Random()
    val age =  random.nextInt(100)
    val update = new UpdateTableStatment(List("test"),
      Map("age" ->s"$age"),Map("name" -> "'dad'"))
    expectedResults = age :: expectedResults
  }

  def makeRead(): Boolean =
  {

    val result = WebServiceUtils.executeSelect(query)
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
  def goodResponse(response:String):Boolean =
  {
    response.equals(firstResult) || expectedResults.exists(num => response.contains(num))
  }


}
