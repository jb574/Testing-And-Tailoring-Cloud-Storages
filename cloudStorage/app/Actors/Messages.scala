package Actors
import models.SQLStatementHelper.MutableSQLStatement

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
   case class MakeConsistent()
  case class DatabaseOperation(update:MutableSQLStatement)
  case class TerminationRequest()
  case class TestMessage()
  case class RequestVote()
  case class ConcernedHealthRequest()
  case class MasterAlive()
  case class WonVote()
  case class InformationRequest()
  case class RequestNewVote()
}
