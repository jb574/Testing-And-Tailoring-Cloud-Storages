package sample


/**
 * this holds the second sample application
 * @author Jack Davey
 * @version 3rd July 2015
 */
object DoubleWriterService
{
  /**
   *   this is the second sample application
   *   This uses two writers, and loops
   *   until eithe rof them has a value that it has never seen before
   * @return a string outuptu.
   */
   def runSecondSample():String =
   {
       val first = new Writer
        val second = new Writer
         var seenQueries = 0
        var result = ""
       var abnormalityDetected = false
        while(!abnormalityDetected)
        {
          first.makeWrite()
          second.makeWrite()
           seenQueries = seenQueries + 1
          if(first.makeRead() || second.makeRead())
          {
            result = s"it took $seenQueries tries to get an inconsistent val"
            abnormalityDetected = true
          }
        }
     result
   }
}
