package sample

import models.SelectStatementHelper.SelectStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment
import java.util.Random
/**
 * sampel prorgam that
 * demonstates  that eventual consistency is workign efffectively
 * @author Jack Davey
 * @version 3rd July 2015
 */
object SingleWriterSample
{
    def runSample():String =
    {
      val random = new Random()
       var age =  random.nextInt(100)
      val query = new SelectStatement(List("test"),"*","")
      var response = WebServiceUtils.executeSelect(query)
      println(response)
      var result = ""
      val update = new UpdateTableStatment(List("test"),
        Map("age" ->s"$age"),Map("name" -> "'dad'"))
      var queriesMade = 0
       WebServiceUtils.executeUpdate(update)
      var consistentResults = 0
      while(consistentResults < 3)
      {
        result = WebServiceUtils.executeSelect(query)
        queriesMade = queriesMade + 1
       // println("result is " + result)
        //println("response is " + response)
        if(!result.equals(response))
        {
          consistentResults = consistentResults + 1
          age =  random.nextInt(100)
          var response = WebServiceUtils.executeSelect(query)
        }
      }
      "it took " + queriesMade  + " attempts to get 4 inconsistent results"
  }
}
