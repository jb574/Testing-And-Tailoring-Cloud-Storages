package models

import models.InsertStatmentHelper.InsertStatment
import play.api.test._

/**
 * Created by jackdavey on 30/06/15.
 */
class insertTableStatementTest extends PlaySpecification
{
  "an insert table statement" should
  {
     "behave like real SQL when converted to text" in new WithApplication()
     {
       val statement = new InsertStatment(List("persons"),Map("name" -> "'jack","age" -> "22"))
       statement.getNewSQLStatement.equals("insert into persons (name,age) values ('jack,22);")
     }
  }
}
