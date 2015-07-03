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
       val age =  random.nextInt(100)
      val query = new SelectStatement(List("test"),"*","")
      val response = WebServiceUtils.executeSelect(query)
      println(response)
      var result = ""
      val update = new UpdateTableStatment(List("test"),
        Map("age" ->s"$age"),Map("name" -> "'dad'"))
      var queriesMade = 0
       WebServiceUtils.executeUpdate(update)
      while(true)
      {
        result = WebServiceUtils.executeSelect(query)
        queriesMade = queriesMade + 1
        println("result is " + result)
        println("response is " + response)
        if(result.equals(response))
        {
          return "it took " + queriesMade  + " attempts to get an inconsistent result"
        }
      }
      "it took " + queriesMade  + " attempts to get an inconsistent result"
  }
}
