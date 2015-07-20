package models

import models.CreateTableStatementHelpers.CreateTableStatement
import play.api.test.{WithApplication, PlaySpecification}

/**
 * Created by jackdavey on 29/06/15.
 */
class CreateTableStatementTest extends PlaySpecification {

  "a create table statement" should {
    "only work on one table" in new WithApplication() {
      var res = false
      try {
        val command = new CreateTableStatement(List("hello", "goodbye"),
          List(), "")
      }
      catch {
        case error: IllegalArgumentException => res = true
      }
      res == true
    }
  }

  "a create table statement" should
  {
    "be capbaple of producing an sql represnetation " +
      "that matches real sql" in new WithApplication()
    {
      var command = new CreateTableStatement(List("persons"),
        List("PersonID int","LastName varchar(255)" ),"")
      assert( command.getNewSQLStatement.equals("create table persons (id integer not null unique, PersonID int,LastName varchar(255)) engine innodb;"))
    }
  }



}
