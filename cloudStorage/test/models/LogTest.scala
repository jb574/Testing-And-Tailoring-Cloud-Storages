package models

import play.api.test.PlaySpecification
import play.api.test._
import models.LogHelper
/**
 * Created by jackdavey on 30/06/15.
 */
class LogTest extends PlaySpecification
{
  "a call to addLogEntry" should
  {
    "store that entry" in new WithApplication()
    {
       val msg = "hi there"
       LogHelper.addLogEntry(msg)
       assert(LogHelper.jsonVersion.toString().contains(msg))
    }
  }
}
