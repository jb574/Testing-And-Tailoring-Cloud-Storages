package controllers


import models.SelectStatementHelper.SelectStatement
import play.api.mvc._
import sample.{DynamoDBInterface, WebServiceUtils, SingleWriterSample, DoubleWriterService}

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
     Ok(SingleWriterSample.runSample())
  }


  def amazonTest = Action
  {
       Ok(DynamoDBInterface.preformRead().toString)
  }

  def test = Action
  {
    val query = new SelectStatement(List("test"),"*","")
    Ok(WebServiceUtils.executeSelect(query))
  }


  def runSecondSample = Action
  {
    Ok(DoubleWriterService.runSecondSample())
  }


}
