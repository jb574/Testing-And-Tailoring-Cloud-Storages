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

  def writeVal(db:DatabaseConnector):Int =
  {
    val result =  new Random().nextInt(100)
    db.write(result)
    result
  }

   def runSample(db:DatabaseConnector):String =
   {
     println()
     println()
     println("NEW RUN")
     var index = 0
     while(index < 100)
     {
       writeVal(db)
       index = index + 1
     }
     var result = writeVal(db)
     println(s"looking for value $result")
     var loopCounter = 0
     var newResult = db.read()
     var attemptCounter = 0
     while(loopCounter < 5)
     {
       if (db.read() == result)
       {
         loopCounter = loopCounter + 1
        result =   writeVal(db)

       }
       else
       {
         loopCounter = 0
         attemptCounter = attemptCounter + 1
       }
     }

     s"it took $attemptCounter attempts before consistency was achieved"
   }
}
