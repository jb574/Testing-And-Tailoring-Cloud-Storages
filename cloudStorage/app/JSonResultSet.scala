import play.api.libs.json.Json

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
/**
 * class that allows me to create a
 * representation of a result set from teh database
 * that can easily be fonverted to JSON.
 * @author Jack Davey
 * @version 10th June 2015
 */
object JSonResultSet
{

  /**
   * as above
   */
   case class JSonResultSet()
   {
       private  var results:ArrayBuffer[HashMap[String,String]]  = new
           ArrayBuffer[mutable.HashMap[String, String]]()


     /**
      * adds a row to the
      * end of this result set
      * @param row the row to add
      */
       def addRow(row:HashMap[String,String]) =
       {
         results += row
       }

      override def toString():String =
      {
        var result = ""
        for(index <- 0 to results.size)
        {
          result = result + " " + index + "."
            val row = results(index)
            for((name,data) <- row)
            {
               result = result + " name: " + name
              result = result + " value: " + data
            }
        }
        result
      }

   }



}
