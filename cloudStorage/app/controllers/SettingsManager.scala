package controllers

/**
 * class to manage the configurable properties of the application
 * @author Jack Davey
 * @version 15th July 2015
 */
object SettingsManager
{
   private var configSettings:Map[String,Int] = Map()
  addValue("timeTilNextConsistencySweep",100)
  addValue("chanceOfGoodResult",0)
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
