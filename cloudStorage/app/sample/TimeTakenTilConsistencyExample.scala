package sample

import scala.util.Random

/**
 * Created by jackdavey on 10/08/15.
 */
object TimeTakenTilConsistencyExample
{

  def isConsistentResult(loopCounter:Int,oldResult:Int,newResult:Int):Int =
  {
    if(oldResult == newResult)
    {
      loopCounter + 1
    }
    else
    {
      0
    }
  }

   def runSample(db:DatabaseConnector):String =
   {
     println()
     println()
     println("NEW RUN")
     val result =  new Random().nextInt(100)
     db.write(result)
     println(s"looking for value $result")
     var loopCounter = 0
     var newResult = db.read()
     var attemptCounter = 0
     while(loopCounter < 5)
     {
       if (db.read() == result)
       {
         loopCounter = loopCounter + 1
         println(s"loop counter is now $loopCounter")
       }
       else
       {
         loopCounter = 0
         attemptCounter = attemptCounter + 1
         println("loopcounter back to zero")
       }
     }

     s"it took $attemptCounter attempts before consistency was achieved"
   }
}
