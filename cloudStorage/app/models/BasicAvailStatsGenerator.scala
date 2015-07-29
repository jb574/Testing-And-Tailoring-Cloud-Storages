package models

import java.time.LocalDate

import play.api.libs.json.{JsValue, Json}

/**
 * object that generats stats to be passed to the end user for generating
 * basic ailibility stats
 * @author Jack Davey
 * @version 29th July 2015
 */
object BasicAvailStatsGenerator
{
   class currentStats()
  {
    override  def toString =
    {
      s"percentage  of sucsessful updatas; $percentagegood, failed updates " +
        s"$percentageBad, total : ${sucesses + failures}} $date"
    }

     var sucesses = 0
     var failures = 0
    var date = LocalDate.now()
     var percentagegood:Double = 0
     var percentageBad:Double = 0

    def addGoodResult() = sucesses = sucesses + 1

    def addFailure() = failures = failures + 1

     private def calcPerc(amount:Double,total:Double):Double =
     {
      val temp = amount/total
       println("temp is " + temp)
       println("total is " + temp*100)
       temp*100
     }


    def generateLatestStats =
    {
      date = LocalDate.now()
      val total =   sucesses + failures
      percentagegood = calcPerc(sucesses,total)
      println(s"percentage good is $percentagegood")
      percentageBad = calcPerc(failures,total)
    }

  }

   var current =  new currentStats

  def addSucsess = current.addGoodResult()


  def AddFailure = current.addFailure()


   def recordStats() =
   {
     current.generateLatestStats
     previusRuns = current :: previusRuns
     current =  new currentStats
   }


  def getDisplayableResults():String =
  {
    current.generateLatestStats
    (current :: previusRuns).toString()
  }




  var previusRuns:List[currentStats] = List()

}
