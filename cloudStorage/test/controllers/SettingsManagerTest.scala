package controllers
import play.api.test._
/**
 * Created by jackdavey on 16/07/15.
 */
class SettingsManagerTest  extends PlaySpecification
{
  val standardKey = "timeTilNextConsistencySweep"
   "the settings manager" should
    {
       "retrieve the value for a particular key if it exists" in  new WithApplication
       {
         assert(SettingsManager.retrieveValue(standardKey) == 100)

       }

    }

  "the settings manager" should
  {
    "act upon all updates" in new WithApplication
    {
      SettingsManager.updateKey(standardKey,2)
      assert(SettingsManager.retrieveValue(standardKey) == 2)
    }
  }




  "the settings manager" should
  {
    " trhow an excpetion if we try to create a key" in new WithApplication()
    {
      var result = false
      try
      {
        SettingsManager.updateKey("doo",1)
      }
      catch
      {
        case good:IllegalArgumentException => result = true
      }
      assert(result)
    }
  }

  "the settings manager" should
    {
      " trhow an excpetion if we try to create a key" in new WithApplication()
      {
        var result = false
        try
        {
          SettingsManager.retrieveValue("doo")
        }
        catch
          {
            case good:IllegalArgumentException => result = true
          }
        assert(result)
      }
    }

}
