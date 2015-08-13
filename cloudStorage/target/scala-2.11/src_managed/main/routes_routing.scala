// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:e0e7207ef877f5e3ac91900793f985b8886020fd
// @DATE:Wed Aug 12 14:08:15 BST 2015


import scala.language.reflectiveCalls
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString

object Routes extends Router.Routes {

import ReverseRouteContext.empty

private var _prefix = "/"

def setPrefix(prefix: String): Unit = {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:9
private[this] lazy val controllers_Application_testAction0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("testold"))))
private[this] lazy val controllers_Application_testAction0_invoker = createInvoker(
controllers.Application.testAction,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "testAction", Nil,"GET", """these urls are for the very simple pieces of code
 i developed as part of the first tieration
 they dont contribute anythign useful to the
rest of the application""", Routes.prefix + """testold"""))
        

// @LINE:10
private[this] lazy val controllers_Application_listQueries1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("seenQueries"))))
private[this] lazy val controllers_Application_listQueries1_invoker = createInvoker(
controllers.Application.listQueries,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listQueries", Nil,"GET", """""", Routes.prefix + """seenQueries"""))
        

// @LINE:11
private[this] lazy val controllers_Application_saveQuery2_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addQuery"))))
private[this] lazy val controllers_Application_saveQuery2_invoker = createInvoker(
controllers.Application.saveQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveQuery", Nil,"POST", """""", Routes.prefix + """addQuery"""))
        

// @LINE:12
private[this] lazy val controllers_Application_saveMutableQuery3_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateDataOld"))))
private[this] lazy val controllers_Application_saveMutableQuery3_invoker = createInvoker(
controllers.Application.saveMutableQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveMutableQuery", Nil,"POST", """""", Routes.prefix + """updateDataOld"""))
        

// @LINE:18
private[this] lazy val controllers_BackEnd_createTable4_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("createTable"))))
private[this] lazy val controllers_BackEnd_createTable4_invoker = createInvoker(
controllers.BackEnd.createTable,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "createTable", Nil,"POST", """These urls  are the services that form
 my implementation of eventual consistency""", Routes.prefix + """createTable"""))
        

// @LINE:19
private[this] lazy val controllers_BackEnd_dropTable5_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("dropTable"))))
private[this] lazy val controllers_BackEnd_dropTable5_invoker = createInvoker(
controllers.BackEnd.dropTable,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "dropTable", Nil,"POST", """""", Routes.prefix + """dropTable"""))
        

// @LINE:20
private[this] lazy val controllers_BackEnd_update6_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateData"))))
private[this] lazy val controllers_BackEnd_update6_invoker = createInvoker(
controllers.BackEnd.update,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "update", Nil,"POST", """""", Routes.prefix + """updateData"""))
        

// @LINE:21
private[this] lazy val controllers_BackEnd_getLogOutput7_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("log"))))
private[this] lazy val controllers_BackEnd_getLogOutput7_invoker = createInvoker(
controllers.BackEnd.getLogOutput,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "getLogOutput", Nil,"GET", """""", Routes.prefix + """log"""))
        

// @LINE:22
private[this] lazy val controllers_BackEnd_insert8_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("insert"))))
private[this] lazy val controllers_BackEnd_insert8_invoker = createInvoker(
controllers.BackEnd.insert,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "insert", Nil,"POST", """""", Routes.prefix + """insert"""))
        

// @LINE:23
private[this] lazy val controllers_BackEnd_delete9_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("delete"))))
private[this] lazy val controllers_BackEnd_delete9_invoker = createInvoker(
controllers.BackEnd.delete,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "delete", Nil,"POST", """""", Routes.prefix + """delete"""))
        

// @LINE:24
private[this] lazy val controllers_BackEnd_makeConsistent10_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("makeConsistent"))))
private[this] lazy val controllers_BackEnd_makeConsistent10_invoker = createInvoker(
controllers.BackEnd.makeConsistent,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "makeConsistent", Nil,"GET", """""", Routes.prefix + """makeConsistent"""))
        

// @LINE:25
private[this] lazy val controllers_BackEnd_changeProperty11_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateVal/name/value"))))
private[this] lazy val controllers_BackEnd_changeProperty11_invoker = createInvoker(
controllers.BackEnd.changeProperty(fakeValue[String], fakeValue[Int]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "changeProperty", Seq(classOf[String], classOf[Int]),"POST", """""", Routes.prefix + """updateVal/name/value"""))
        

// @LINE:26
private[this] lazy val controllers_BackEnd_getInconsistentUPdateInfo12_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("allUpdates"))))
private[this] lazy val controllers_BackEnd_getInconsistentUPdateInfo12_invoker = createInvoker(
controllers.BackEnd.getInconsistentUPdateInfo,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "getInconsistentUPdateInfo", Nil,"GET", """""", Routes.prefix + """allUpdates"""))
        

// @LINE:27
private[this] lazy val controllers_BackEnd_runEventuallyConsistentQuery13_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("select"))))
private[this] lazy val controllers_BackEnd_runEventuallyConsistentQuery13_invoker = createInvoker(
controllers.BackEnd.runEventuallyConsistentQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "runEventuallyConsistentQuery", Nil,"POST", """""", Routes.prefix + """select"""))
        

// @LINE:28
private[this] lazy val controllers_BackEnd_getAllPossibilities14_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("selectAllPossible"))))
private[this] lazy val controllers_BackEnd_getAllPossibilities14_invoker = createInvoker(
controllers.BackEnd.getAllPossibilities,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "getAllPossibilities", Nil,"POST", """""", Routes.prefix + """selectAllPossible"""))
        

// @LINE:29
private[this] lazy val controllers_SampleApplications_amazonTest15_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("amazonTest"))))
private[this] lazy val controllers_SampleApplications_amazonTest15_invoker = createInvoker(
controllers.SampleApplications.amazonTest,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "amazonTest", Nil,"GET", """""", Routes.prefix + """amazonTest"""))
        

// @LINE:30
private[this] lazy val controllers_BackEnd_getAvailibilityStats16_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("stats"))))
private[this] lazy val controllers_BackEnd_getAvailibilityStats16_invoker = createInvoker(
controllers.BackEnd.getAvailibilityStats,
HandlerDef(this.getClass.getClassLoader, "", "controllers.BackEnd", "getAvailibilityStats", Nil,"GET", """""", Routes.prefix + """stats"""))
        

// @LINE:32
private[this] lazy val controllers_SampleApplications_runFirstSample17_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("sample1"))))
private[this] lazy val controllers_SampleApplications_runFirstSample17_invoker = createInvoker(
controllers.SampleApplications.runFirstSample,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "runFirstSample", Nil,"GET", """ the urls below show the ample applications i wrote to help rove my eventual consistency implementation does the jo""", Routes.prefix + """sample1"""))
        

// @LINE:33
private[this] lazy val controllers_SampleApplications_runSecondSample18_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("sample2"))))
private[this] lazy val controllers_SampleApplications_runSecondSample18_invoker = createInvoker(
controllers.SampleApplications.runSecondSample,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "runSecondSample", Nil,"GET", """""", Routes.prefix + """sample2"""))
        

// @LINE:34
private[this] lazy val controllers_SampleApplications_amazonTest19_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("test"))))
private[this] lazy val controllers_SampleApplications_amazonTest19_invoker = createInvoker(
controllers.SampleApplications.amazonTest,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "amazonTest", Nil,"GET", """""", Routes.prefix + """test"""))
        

// @LINE:35
private[this] lazy val controllers_SampleApplications_runAvSample20_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("avsam"))))
private[this] lazy val controllers_SampleApplications_runAvSample20_invoker = createInvoker(
controllers.SampleApplications.runAvSample,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "runAvSample", Nil,"GET", """""", Routes.prefix + """avsam"""))
        

// @LINE:36
private[this] lazy val controllers_SampleApplications_timeToConsistent21_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("cons"))))
private[this] lazy val controllers_SampleApplications_timeToConsistent21_invoker = createInvoker(
controllers.SampleApplications.timeToConsistent,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "timeToConsistent", Nil,"GET", """""", Routes.prefix + """cons"""))
        

// @LINE:38
private[this] lazy val controllers_Assets_versioned22_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_versioned22_invoker = createInvoker(
controllers.Assets.versioned(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """testold""","""controllers.Application.testAction"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """seenQueries""","""controllers.Application.listQueries"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addQuery""","""controllers.Application.saveQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateDataOld""","""controllers.Application.saveMutableQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """createTable""","""controllers.BackEnd.createTable"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """dropTable""","""controllers.BackEnd.dropTable"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateData""","""controllers.BackEnd.update"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """log""","""controllers.BackEnd.getLogOutput"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """insert""","""controllers.BackEnd.insert"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """delete""","""controllers.BackEnd.delete"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """makeConsistent""","""controllers.BackEnd.makeConsistent"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateVal/name/value""","""controllers.BackEnd.changeProperty(name:String, value:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """allUpdates""","""controllers.BackEnd.getInconsistentUPdateInfo"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """select""","""controllers.BackEnd.runEventuallyConsistentQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """selectAllPossible""","""controllers.BackEnd.getAllPossibilities"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """amazonTest""","""controllers.SampleApplications.amazonTest"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """stats""","""controllers.BackEnd.getAvailibilityStats"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """sample1""","""controllers.SampleApplications.runFirstSample"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """sample2""","""controllers.SampleApplications.runSecondSample"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test""","""controllers.SampleApplications.amazonTest"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """avsam""","""controllers.SampleApplications.runAvSample"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """cons""","""controllers.SampleApplications.timeToConsistent"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.versioned(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:9
case controllers_Application_testAction0_route(params) => {
   call { 
        controllers_Application_testAction0_invoker.call(controllers.Application.testAction)
   }
}
        

// @LINE:10
case controllers_Application_listQueries1_route(params) => {
   call { 
        controllers_Application_listQueries1_invoker.call(controllers.Application.listQueries)
   }
}
        

// @LINE:11
case controllers_Application_saveQuery2_route(params) => {
   call { 
        controllers_Application_saveQuery2_invoker.call(controllers.Application.saveQuery)
   }
}
        

// @LINE:12
case controllers_Application_saveMutableQuery3_route(params) => {
   call { 
        controllers_Application_saveMutableQuery3_invoker.call(controllers.Application.saveMutableQuery)
   }
}
        

// @LINE:18
case controllers_BackEnd_createTable4_route(params) => {
   call { 
        controllers_BackEnd_createTable4_invoker.call(controllers.BackEnd.createTable)
   }
}
        

// @LINE:19
case controllers_BackEnd_dropTable5_route(params) => {
   call { 
        controllers_BackEnd_dropTable5_invoker.call(controllers.BackEnd.dropTable)
   }
}
        

// @LINE:20
case controllers_BackEnd_update6_route(params) => {
   call { 
        controllers_BackEnd_update6_invoker.call(controllers.BackEnd.update)
   }
}
        

// @LINE:21
case controllers_BackEnd_getLogOutput7_route(params) => {
   call { 
        controllers_BackEnd_getLogOutput7_invoker.call(controllers.BackEnd.getLogOutput)
   }
}
        

// @LINE:22
case controllers_BackEnd_insert8_route(params) => {
   call { 
        controllers_BackEnd_insert8_invoker.call(controllers.BackEnd.insert)
   }
}
        

// @LINE:23
case controllers_BackEnd_delete9_route(params) => {
   call { 
        controllers_BackEnd_delete9_invoker.call(controllers.BackEnd.delete)
   }
}
        

// @LINE:24
case controllers_BackEnd_makeConsistent10_route(params) => {
   call { 
        controllers_BackEnd_makeConsistent10_invoker.call(controllers.BackEnd.makeConsistent)
   }
}
        

// @LINE:25
case controllers_BackEnd_changeProperty11_route(params) => {
   call(params.fromQuery[String]("name", None), params.fromQuery[Int]("value", None)) { (name, value) =>
        controllers_BackEnd_changeProperty11_invoker.call(controllers.BackEnd.changeProperty(name, value))
   }
}
        

// @LINE:26
case controllers_BackEnd_getInconsistentUPdateInfo12_route(params) => {
   call { 
        controllers_BackEnd_getInconsistentUPdateInfo12_invoker.call(controllers.BackEnd.getInconsistentUPdateInfo)
   }
}
        

// @LINE:27
case controllers_BackEnd_runEventuallyConsistentQuery13_route(params) => {
   call { 
        controllers_BackEnd_runEventuallyConsistentQuery13_invoker.call(controllers.BackEnd.runEventuallyConsistentQuery)
   }
}
        

// @LINE:28
case controllers_BackEnd_getAllPossibilities14_route(params) => {
   call { 
        controllers_BackEnd_getAllPossibilities14_invoker.call(controllers.BackEnd.getAllPossibilities)
   }
}
        

// @LINE:29
case controllers_SampleApplications_amazonTest15_route(params) => {
   call { 
        controllers_SampleApplications_amazonTest15_invoker.call(controllers.SampleApplications.amazonTest)
   }
}
        

// @LINE:30
case controllers_BackEnd_getAvailibilityStats16_route(params) => {
   call { 
        controllers_BackEnd_getAvailibilityStats16_invoker.call(controllers.BackEnd.getAvailibilityStats)
   }
}
        

// @LINE:32
case controllers_SampleApplications_runFirstSample17_route(params) => {
   call { 
        controllers_SampleApplications_runFirstSample17_invoker.call(controllers.SampleApplications.runFirstSample)
   }
}
        

// @LINE:33
case controllers_SampleApplications_runSecondSample18_route(params) => {
   call { 
        controllers_SampleApplications_runSecondSample18_invoker.call(controllers.SampleApplications.runSecondSample)
   }
}
        

// @LINE:34
case controllers_SampleApplications_amazonTest19_route(params) => {
   call { 
        controllers_SampleApplications_amazonTest19_invoker.call(controllers.SampleApplications.amazonTest)
   }
}
        

// @LINE:35
case controllers_SampleApplications_runAvSample20_route(params) => {
   call { 
        controllers_SampleApplications_runAvSample20_invoker.call(controllers.SampleApplications.runAvSample)
   }
}
        

// @LINE:36
case controllers_SampleApplications_timeToConsistent21_route(params) => {
   call { 
        controllers_SampleApplications_timeToConsistent21_invoker.call(controllers.SampleApplications.timeToConsistent)
   }
}
        

// @LINE:38
case controllers_Assets_versioned22_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_versioned22_invoker.call(controllers.Assets.versioned(path, file))
   }
}
        
}

}
     