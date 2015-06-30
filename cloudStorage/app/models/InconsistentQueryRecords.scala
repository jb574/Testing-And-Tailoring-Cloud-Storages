package models

import java.time.{LocalDateTime, LocalDate}

import play.api.libs.json.{JsValue, Json}


/**
 * class that enables management
 * of a record of all the updates
 * floating around the system.
 * none of the updates are stored
 * here directly, only text descriptions of them.
 * @author Jack Davey
 * @version 30th June 2015
 */
object InconsistentQueryRecords
{
     private var inconsistentUpdates:List[String] = List()
      var time:LocalDateTime = LocalDateTime.now()



  /**
   * adds an item tot eh list
   * @param inconsistentUpdate the string represnetation
   *                           of the new update
   */
   def addItem(inconsistentUpdate:String) =
     inconsistentUpdates = inconsistentUpdate :: inconsistentUpdates


  /**
   * clrears all the updates currently in the system.
   */
  def clear() = inconsistentUpdates = List()

  /**
   * @return a version fo this list in JSON to display to end users
   */
  def getUserOutPut():JsValue =
  {
       Json.toJson("consistent at " + time.toString + " "
         + inconsistentUpdates.toString)
  }


  /**
   *
   * @return whether we have any updates at all.
   */
  def isEmpty():Boolean = inconsistentUpdates.isEmpty



}
