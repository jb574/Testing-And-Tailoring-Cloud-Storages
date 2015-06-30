package models

import play.api.test.{WithApplication, PlaySpecification}

/**
 * Created by jackdavey on 30/06/15.
 */
class InconsistentUpdatesLogTest extends PlaySpecification
{
   "the inconsistent updates log" should
  {
    "be empty when cleared" in new WithApplication()
    {
      InconsistentQueryRecords.addItem("hi")
      InconsistentQueryRecords.clear()
      InconsistentQueryRecords.isEmpty()
    }
  }

  "the inconsistent update log " should
  {
    "be able to regurgitatle all seen updates" in new WithApplication()
    {
      InconsistentQueryRecords.addItem("hi")
      InconsistentQueryRecords.getUserOutPut().toString().contains("hi")
    }
  }

}
