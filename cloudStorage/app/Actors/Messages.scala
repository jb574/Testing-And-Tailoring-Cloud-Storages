package Actors

/**
 * this object  contains al of the messages
 * that my system can send
 * @author Jack Davey
 * @version 16th June 2014
 */
object Messages
{
     abstract class logMessage
    case class Message(messageText:String)  extends logMessage
    case class RetrieveLog() extends logMessage
}
