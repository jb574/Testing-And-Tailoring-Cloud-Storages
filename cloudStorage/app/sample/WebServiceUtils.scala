package sample

import models.DeleteStatementHelper.DeleteStatment
import models.InsertStatmentHelper.InsertStatment
import models.SQLStatementHelper.SQLStatement
import models.SelectStatementHelper.SelectStatement
import models.UpdateTableStatmentHelper.UpdateTableStatment
import play.api.libs.ws._
import scala.concurrent._
import play.api.libs.json.{JsValue, Json}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Try, Failure, Success}
 import play.api.Play.current
/**
 * code to call the web services
 * that emulate eventual consistncy
 * y sample programs call these  linksm
 */
object WebServiceUtils
{

    /**
     * method to get data out of the database
     * @param select a select statement represnting the
     *               sql query
     * @return a string reprsenting the result
     */
    def executeSelect(select:SelectStatement):String =
    {
        executeServiceRequest("http://localhost:9000/select",Json.toJson(select))
    }

    /**
     * method to run an sql insert
     * @param insert the  insert to execute
     * @return a string containing the result
     */
    def executeInsert(insert:InsertStatment):String =
    {
        executeServiceRequest("http://localhost:9000/insert",Json.toJson(insert))
    }

    /**
     * method to run an sql delete
     * @param  delete the  delete to execute
     * @return a string containing the result
     */
    def executeDelete(delete:DeleteStatment):String =
    {
        executeServiceRequest("http://localhost:9000/delete",Json.toJson(delete))
    }



    /**
     * method to run an sql update
     * @param update the update to execute
     * @return a string containing the result
     */
    def executeUpdate(update:UpdateTableStatment):String =
    {
        executeServiceRequest("http://localhost:9000/updateData",Json.toJson(update))
    }


    /**
     * main helper function  for clling
     * web services
     * @param url the url to use
     * @param statement the sql statement to run
     * @return
     */
    private def executeServiceRequest(url:String,statement:JsValue):String =
    {
        var result = ""
        val futureResponse: Future[String] =  WS.url(url).post(statement).map(
          response => response.body
        )
        futureResponse.onComplete
        {
          case res => result = result + res
        }
        result
    }
}
