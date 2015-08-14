package controllers


import models.SelectStatementHelper.SelectStatement
import play.api.mvc._
import sample._

/**
 * This class houses
 * the invoking mechanisms for the
 * sample applications that I have written
 * to verify that my emulator
 * meets the BASE properties
 * @author Jack Davey
 * @version 3rd July 2015
 */
object SampleApplications  extends Controller
{
  def runFirstSample = Action
  {
     Ok(SingleWriterSample.runSample(new DynamoDBInterface))
  }


  def percTest = Action
  {
    Ok(s"${PercentageOfConsistantReadsExample.runSample(
      new EmulatorDBInterface, 3
    )}")
  }

  /**
   * code for running and configuring
   * a sample application
   * @param sample the sample application to run
   * @param amazonUsed a boolean determining which of the two services we use
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

  /**
   * code to test the connection
   * with Amazon
   */
  def amazonTest = Action
  {
    val interface = new DynamoDBInterface
    Ok(interface.read().toString)
  }


  def timeToConsistent = Action
  {
    var count = 0
    var result = s""
    Ok(result + s"    " +
      s"${TimeTakenTilConsistencyExample.runSample(new DynamoDBInterface)}")
  }


  /**
   * code to test that the select statement
   * is being run effectively
   */
  def test = Action
  {
    val query = new SelectStatement(List("test"),"*","")
    Ok(WebServiceUtils.executeSelect(query))
  }

  /**
   *  code to test the basic availability
   *  this doesn't actually give the stats
   *  back but it sends 1000 requests to my system
   *  as an appropriate test
   */
  def runAvSample = Action
  {
    BasicAavailabilitySampleOne.runSample(new EmulatorDBInterface)
    Ok("sample run")
  }


  /**
   * runs the second sample with two writers
   */
  def runSecondSample = Action
  {
    Ok(DoubleWriterService.runSecondSample(new DynamoDBInterface))
  }


}
