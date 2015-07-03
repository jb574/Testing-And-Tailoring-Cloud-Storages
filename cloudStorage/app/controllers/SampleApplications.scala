package controllers


import play.api.mvc._
import sample.SingleWriterSample
import sample.DoubleWriterService

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

  def runSecondSample = Action
  {
    Ok(DoubleWriterService.runSecondSample())
  }


}
