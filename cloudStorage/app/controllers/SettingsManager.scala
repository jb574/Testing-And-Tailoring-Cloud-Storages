package controllers

/**
 * class to manage the configurable properties of the application
 * @author Jack Davey
 * @version 15th July 2015
 */
object SettingsManager
{
   private var configSettings:Map[String,Int] = Map()
  LoadConfigCurrent

  def LoadConfigCurrent: Unit =
  {
    addValue("timeTilNextConsistencySweep", 100)
    addValue("chanceOfGoodResult", 50)
    addValue("primServers", 2)
    addValue("secServers", 3)
    addValue("checkUpTime", 20 )
    addValue("lifeTime", 40)
    addValue("avTarget",200)
  }

  def LoadConfigEventualConsistency: Unit =
  {
    addValue("timeTilNextConsistencySweep", 100)
    addValue("chanceOfGoodResult", 100)
    addValue("primServers", 3)
    addValue("secServers", 4)
    addValue("checkUpTime", 20)
    addValue("lifeTime", 40)
  }


  private def addValue(key:String,value:Int) =
  {
    configSettings = configSettings + (key -> value)
  }

  /**
   * change a value in the map
   * the property must exist
   * for it to be changed
   * @param key the name of the property
   * @param value the new value of the property
   * @throws IllegalArgumentException if the desired propertydoes not exist
   */
   def updateKey(key:String,value:Int) =
  {
    if(configSettings.contains(key))
    {
        configSettings = configSettings + (key -> value)
    }
    else
    {
      throw new IllegalArgumentException("key cannot be found")
    }
  }


  /**
   * retrieves a value for a given key
   * @param key the key to remove
   * @return  the appropriate value
   */
  def retrieveValue(key:String):Int =
  {
    if(configSettings.contains(key))
    {
      configSettings(key)
    }
    else
    {
      throw new IllegalArgumentException("key cannot be found")
    }
  }

}
