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
    def runSample(db:DatabaseConnector):String =
    {
      println("getting goign")
      val random = new Random()
       var age =  random.nextInt(100)
      println(s"the age is $age")
      var response = db.read()
      println(response)
      var result = -1
      var queriesMade = 0
      println(s"age is $age")
      db.write(age)
      var consistentResults = 0
      while(consistentResults < 4)
      {
        result = db.read()
        queriesMade = queriesMade + 1
       // println("result is " + result)
        //println("response is " + response)
        if(result != age)
        {
          consistentResults = consistentResults + 1
          age =  random.nextInt(100)
          db.write(age)
        }
      }
      "it took " + consistentResults  + " attempts to get 4 inconsistent results"
  }
}
