package sample

import scala.util.Random

/**
 * Created by jackdavey on 14/08/15.
 */
object PercentageOfConsistantReadsExample
{
  def runSample(db:DatabaseConnector,numberOfWrites:Int):String =
  {
     var counter = 0
     var res = 0
    var rand = new Random()
     while(counter < numberOfWrites)
     {
       res = rand.nextInt(100)
       db.write(res)
       counter = counter + 1
     }
    counter =0
    var read = 0
    var  perc = 0
    while(counter < 100)
    {
      read = db.read()
      if(read == res)
      {
        perc = perc + 1
      }
      counter = counter + 1
    }
   s" percentage of results consistent : $perc"
  }
}
