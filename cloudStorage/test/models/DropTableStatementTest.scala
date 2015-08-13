package models

import models.CreateTableStatementHelpers.CreateTableStatement
import models.DropTableStatementHelper.DropTableStatement
import play.api.test.{PlaySpecification, WithApplication}

/**
 * Created by jackdavey on 29/06/15.
 */
class DropTableStatementTest extends  PlaySpecification
{
  "a drop table statement" should {
    "only work on one table" in new WithApplication() {
      var res = false
      try {
        val command = new DropTableStatement(List("hello","bad"))
      }
      catch {
        case error: IllegalArgumentException => res = true
      }
      res == true
    }
  }
  "a drop table statement" should
  {
    "be capable of producign perfect SQL" in new WithApplication()
    {
       val command = new DropTableStatement(List("persons"))
       assert(command.getNewSQLStatement.equals("drop table persons;"))
    }
  }

}
