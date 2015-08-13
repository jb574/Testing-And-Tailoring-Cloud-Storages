package sample

import models.SelectStatementHelper.SelectStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment

import scala.util.Random


/**
 * class  THAT ALLOWS FOR  RUNNING MY EMULATOR TO GET AT INFORMATION
 * @author Jack Davey
 * @version 4th August 2015
 */
class EmulatorDBInterface  extends  DatabaseConnector
{

  /**
   * reads a value from the database
   * @return a value from the underlying database where the name is
   *         "dad"
   */
  def read(): Int =
  {
    val query = new SelectStatement(List("test"),"age","where name = 'dad'")
    val response = WebServiceUtils.executeSelect(query)
    val betterResponse = response.replace("age","").
      replaceAll("\\s","").replaceAll("\"", "");
    println(s"read value is $betterResponse")
    Integer.parseInt(betterResponse)
  }

  /**
   * writes  a value to the database
   * @param age the new age
   */
  def write(age: Int) =
  {
    println(s"written value is $age")
    val update = new UpdateTableStatment(List("test"),
      Map("age" ->s"$age"),Map("name" -> "'dad'"))
    WebServiceUtils.executeUpdate(update)
  }

}
