package models

import play.api.test.{WithApplication, PlaySpecification}

/**
 * Created by jackdavey on 29/06/15.
 */
class UpdateStatementTest extends PlaySpecification
{
  "an update statment" should
  {
    "produce proper sql" in new WithApplication()
    {
      val statment = UpdateTableStatmentHelper.UpdateTableStatment(
      List("person"),Map("'age'" -> "32"),Map("name" -> "jack"))
      assert(statment.getNewSQLStatement.equals("update person set 'age' = 32 where name = jack ; "))
    }
  }
}
