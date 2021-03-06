package models

import java.time.LocalDateTime
import java.util.Date

import play.api.libs.json.Json


/**
 * backing machinery for the logger actor
 * to do its work
 * @author Jack Davey
 * @version 16th June 2015
 */
object LogHelper
{

  /**
   * class that represents an entry in the log
   * @author Jack Davey
   * @version 16th June 2015
   * @param msg the message of this log entry
   */
  case class LogEntry( msg:String, date:String)
  {
    override def toString =
    {
      s"message: $msg, date: $date${System.lineSeparator()}}"
    }
  }

  private var log = List[LogEntry]()
  implicit val LogWrites = Json.writes[LogEntry]
  implicit val LogReads = Json.reads[LogEntry]


  /**
   * add a new log entry to the log
   * @param message the new message to add
   */
  def addLogEntry(message:String) =
  {
    val entry =  LogEntry(message, LocalDateTime.now().toString)
    println(entry.toString)
    log = entry :: log
  }
  /**
   * @return a json version of the log
   */
  def jsonVersion = Json.toJson(log)


}
