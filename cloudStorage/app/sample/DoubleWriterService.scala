package sample


/**
 * this holds the second sample application
 * @author Jack Davey
 * @version 3rd July 2015
 */
object DoubleWriterService extends App
{

  /**
   *   this is the second sample application
   *   This uses two writers, and loops
   *   until eithe rof them has a value that it has never seen before
   * @return a string outuptu.
   */
   def runSecondSample(db: DatabaseConnector):String =
   {
       val first = new Writer(db)
        val second = new Writer(db)
         var seenQueries = 0
        var result = ""
       var abnormalityDetected = false
        while(!abnormalityDetected)
        {

           seenQueries = seenQueries + 1
          if(!first.makeWriteAndRead() || !second.makeWriteAndRead())
          {
            result = s"it took $seenQueries tries to get an inconsistent val"
            abnormalityDetected = true
          }
        }
     result
   }



}
