package models

import models.DeleteStatementHelper.DeleteStatment
import play.api.test
import play.api.test.{WithApplication, PlaySpecification}


/**
 * Created by jackdavey on 30/06/15.
 */
class DeleteTableStatementTest extends PlaySpecification
{
   "a delte table statement" should
  {
    "be capable of bing run in a database when convertted to a string" in new WithApplication()
    {
      var statemment = new DeleteStatment(List("persons"),Map("name" -> "Jack"))
      assert(statemment.getNewSQLStatement.equals("delete from persons where name = Jack  ; "))
    }
  }
}
