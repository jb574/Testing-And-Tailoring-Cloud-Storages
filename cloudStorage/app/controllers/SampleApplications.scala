package controllers


import models.SelectStatementHelper.SelectStatement
import play.api.mvc._
import sample._

/**
 * This class houses
 * the invokign mechansims for the
 * sample applications that I have written
 * to verify that  my emulator
 * meets the BASE properties
 * @author Jack Davey
 * @version 3rd June 2015
 */
object SampleApplications  extends Controller
{
  def runFirstSample = Action
  {
     Ok(SingleWriterSample.runSample(new EmulatorDBInterface))
  }

  /**
   * code for running and configuring
   * a sample application
   * @param sample the sample application to run
   * @param amazonUsed  a boolean determining which of the two services we use
   *                    either DynamoDB or the emulator
   * @return  the result of the sample run.
   */
  def runSample(sample:(DatabaseConnector) => String,amazonUsed:Boolean):String =
  {
     if(amazonUsed)
     {
       sample(new DynamoDBInterface)
     }
    else
     {
       sample(new EmulatorDBInterface)
     }
  }

  def amazonTest = Action
  {
    val interface = new DynamoDBInterface
    Ok(interface.read().toString)
  }

  def test = Action
  {
    val query = new SelectStatement(List("test"),"*","")
    Ok(WebServiceUtils.executeSelect(query))
  }


  def runSecondSample = Action
  {
    Ok(DoubleWriterService.runSecondSample(new DynamoDBInterface))
  }


}
