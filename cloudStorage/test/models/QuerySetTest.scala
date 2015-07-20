package models

import Actors.Logger
import akka.actor.{Props, ActorRef}
import models.InsertStatmentHelper.InsertStatment
import play.api.test._
import play.libs.Akka


/**
 * Created by jackdavey on 30/06/15.
 */
class QuerySetTest extends  PlaySpecification
{
   "two querysets that share the same tables" should
  {
    "be relevant to each other" in new WithApplication()
    {
      val statement = new InsertStatment(List("persons"),Map())
      val first = new QuerySet(statement,0)
      assert(first.isUpdateRelavant(statement))
    }
  }

  "tow querysets that share the same tables" should
  {
    "return true when comparing with each other" in new WithApplication()
    {
      val statement = new InsertStatment(List("persons"),Map())
      val first = new QuerySet(statement,0)
      val second = new QuerySet(first)
      assert(first.dealQithSameeData(second))
    }
  }

 "after tow querysets have been merged, the second" should
  {
    "have both sets of elements and vector clocks" in new WithApplication()
    {
      val statement = new InsertStatment(List("persons"),Map())
      val first = new QuerySet(statement,0)
      val secondstatement = new InsertStatment(List("persons"),Map())
      val second = new QuerySet(statement,0)
      first.addNewQuery(new InsertStatment(List("persons"),Map()),0)
      first.mergeQuerySets(second)
      assert(first.vectorClocks.size >= 1  && first.queries.size >= 1)
    }
  }




}
