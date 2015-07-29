// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:68e74a763b0a4766d09cb7164605e0dd5b2a7198
// @DATE:Wed Jul 29 14:05:19 BST 2015


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
private[this] lazy val controllers_FrontEnd_createTable4_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("createTable"))))
private[this] lazy val controllers_FrontEnd_createTable4_invoker = createInvoker(
controllers.FrontEnd.createTable,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "createTable", Nil,"POST", """These urls  are the services that form
 my implementation of eventual consistency""", Routes.prefix + """createTable"""))
        

// @LINE:19
private[this] lazy val controllers_FrontEnd_dropTable5_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("dropTable"))))
private[this] lazy val controllers_FrontEnd_dropTable5_invoker = createInvoker(
controllers.FrontEnd.dropTable,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "dropTable", Nil,"POST", """""", Routes.prefix + """dropTable"""))
        

// @LINE:20
private[this] lazy val controllers_FrontEnd_update6_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateData"))))
private[this] lazy val controllers_FrontEnd_update6_invoker = createInvoker(
controllers.FrontEnd.update,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "update", Nil,"POST", """""", Routes.prefix + """updateData"""))
        

// @LINE:21
private[this] lazy val controllers_FrontEnd_getLogOutput7_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("log"))))
private[this] lazy val controllers_FrontEnd_getLogOutput7_invoker = createInvoker(
controllers.FrontEnd.getLogOutput,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getLogOutput", Nil,"GET", """""", Routes.prefix + """log"""))
        

// @LINE:22
private[this] lazy val controllers_FrontEnd_insert8_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("insert"))))
private[this] lazy val controllers_FrontEnd_insert8_invoker = createInvoker(
controllers.FrontEnd.insert,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "insert", Nil,"POST", """""", Routes.prefix + """insert"""))
        

// @LINE:23
private[this] lazy val controllers_FrontEnd_delete9_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("delete"))))
private[this] lazy val controllers_FrontEnd_delete9_invoker = createInvoker(
controllers.FrontEnd.delete,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "delete", Nil,"POST", """""", Routes.prefix + """delete"""))
        

// @LINE:24
private[this] lazy val controllers_FrontEnd_makeConsistent10_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("makeConsistent"))))
private[this] lazy val controllers_FrontEnd_makeConsistent10_invoker = createInvoker(
controllers.FrontEnd.makeConsistent,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "makeConsistent", Nil,"GET", """""", Routes.prefix + """makeConsistent"""))
        

// @LINE:25
private[this] lazy val controllers_FrontEnd_changeProperty11_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateVal/name/value"))))
private[this] lazy val controllers_FrontEnd_changeProperty11_invoker = createInvoker(
controllers.FrontEnd.changeProperty(fakeValue[String], fakeValue[Int]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "changeProperty", Seq(classOf[String], classOf[Int]),"POST", """""", Routes.prefix + """updateVal/name/value"""))
        

// @LINE:26
private[this] lazy val controllers_FrontEnd_getInconsistentUPdateInfo12_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("allUpdates"))))
private[this] lazy val controllers_FrontEnd_getInconsistentUPdateInfo12_invoker = createInvoker(
controllers.FrontEnd.getInconsistentUPdateInfo,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getInconsistentUPdateInfo", Nil,"GET", """""", Routes.prefix + """allUpdates"""))
        

// @LINE:27
private[this] lazy val controllers_FrontEnd_runEventuallyConsistentQuery13_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("select"))))
private[this] lazy val controllers_FrontEnd_runEventuallyConsistentQuery13_invoker = createInvoker(
controllers.FrontEnd.runEventuallyConsistentQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "runEventuallyConsistentQuery", Nil,"POST", """""", Routes.prefix + """select"""))
        

// @LINE:28
private[this] lazy val controllers_FrontEnd_getAllPossiblilities14_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("selectAllPossible"))))
private[this] lazy val controllers_FrontEnd_getAllPossiblilities14_invoker = createInvoker(
controllers.FrontEnd.getAllPossiblilities,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getAllPossiblilities", Nil,"POST", """""", Routes.prefix + """selectAllPossible"""))
        

// @LINE:29
private[this] lazy val controllers_SampleApplications_amazonTest15_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("amazonTest"))))
private[this] lazy val controllers_SampleApplications_amazonTest15_invoker = createInvoker(
controllers.SampleApplications.amazonTest,
HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "amazonTest", Nil,"GET", """""", Routes.prefix + """amazonTest"""))
        

// @LINE:30
private[this] lazy val controllers_FrontEnd_getAvailibilityStats16_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("stats"))))
private[this] lazy val controllers_FrontEnd_getAvailibilityStats16_invoker = createInvoker(
controllers.FrontEnd.getAvailibilityStats,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getAvailibilityStats", Nil,"GET", """""", Routes.prefix + """stats"""))
        

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
        

// @LINE:37
private[this] lazy val controllers_Assets_versioned20_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_versioned20_invoker = createInvoker(
controllers.Assets.versioned(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """testold""","""controllers.Application.testAction"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """seenQueries""","""controllers.Application.listQueries"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addQuery""","""controllers.Application.saveQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateDataOld""","""controllers.Application.saveMutableQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """createTable""","""controllers.FrontEnd.createTable"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """dropTable""","""controllers.FrontEnd.dropTable"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateData""","""controllers.FrontEnd.update"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """log""","""controllers.FrontEnd.getLogOutput"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """insert""","""controllers.FrontEnd.insert"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """delete""","""controllers.FrontEnd.delete"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """makeConsistent""","""controllers.FrontEnd.makeConsistent"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateVal/name/value""","""controllers.FrontEnd.changeProperty(name:String, value:Int)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """allUpdates""","""controllers.FrontEnd.getInconsistentUPdateInfo"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """select""","""controllers.FrontEnd.runEventuallyConsistentQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """selectAllPossible""","""controllers.FrontEnd.getAllPossiblilities"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """amazonTest""","""controllers.SampleApplications.amazonTest"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """stats""","""controllers.FrontEnd.getAvailibilityStats"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """sample1""","""controllers.SampleApplications.runFirstSample"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """sample2""","""controllers.SampleApplications.runSecondSample"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test""","""controllers.SampleApplications.amazonTest"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.versioned(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
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
case controllers_FrontEnd_createTable4_route(params) => {
   call { 
        controllers_FrontEnd_createTable4_invoker.call(controllers.FrontEnd.createTable)
   }
}
        

// @LINE:19
case controllers_FrontEnd_dropTable5_route(params) => {
   call { 
        controllers_FrontEnd_dropTable5_invoker.call(controllers.FrontEnd.dropTable)
   }
}
        

// @LINE:20
case controllers_FrontEnd_update6_route(params) => {
   call { 
        controllers_FrontEnd_update6_invoker.call(controllers.FrontEnd.update)
   }
}
        

// @LINE:21
case controllers_FrontEnd_getLogOutput7_route(params) => {
   call { 
        controllers_FrontEnd_getLogOutput7_invoker.call(controllers.FrontEnd.getLogOutput)
   }
}
        

// @LINE:22
case controllers_FrontEnd_insert8_route(params) => {
   call { 
        controllers_FrontEnd_insert8_invoker.call(controllers.FrontEnd.insert)
   }
}
        

// @LINE:23
case controllers_FrontEnd_delete9_route(params) => {
   call { 
        controllers_FrontEnd_delete9_invoker.call(controllers.FrontEnd.delete)
   }
}
        

// @LINE:24
case controllers_FrontEnd_makeConsistent10_route(params) => {
   call { 
        controllers_FrontEnd_makeConsistent10_invoker.call(controllers.FrontEnd.makeConsistent)
   }
}
        

// @LINE:25
case controllers_FrontEnd_changeProperty11_route(params) => {
   call(params.fromQuery[String]("name", None), params.fromQuery[Int]("value", None)) { (name, value) =>
        controllers_FrontEnd_changeProperty11_invoker.call(controllers.FrontEnd.changeProperty(name, value))
   }
}
        

// @LINE:26
case controllers_FrontEnd_getInconsistentUPdateInfo12_route(params) => {
   call { 
        controllers_FrontEnd_getInconsistentUPdateInfo12_invoker.call(controllers.FrontEnd.getInconsistentUPdateInfo)
   }
}
        

// @LINE:27
case controllers_FrontEnd_runEventuallyConsistentQuery13_route(params) => {
   call { 
        controllers_FrontEnd_runEventuallyConsistentQuery13_invoker.call(controllers.FrontEnd.runEventuallyConsistentQuery)
   }
}
        

// @LINE:28
case controllers_FrontEnd_getAllPossiblilities14_route(params) => {
   call { 
        controllers_FrontEnd_getAllPossiblilities14_invoker.call(controllers.FrontEnd.getAllPossiblilities)
   }
}
        

// @LINE:29
case controllers_SampleApplications_amazonTest15_route(params) => {
   call { 
        controllers_SampleApplications_amazonTest15_invoker.call(controllers.SampleApplications.amazonTest)
   }
}
        

// @LINE:30
case controllers_FrontEnd_getAvailibilityStats16_route(params) => {
   call { 
        controllers_FrontEnd_getAvailibilityStats16_invoker.call(controllers.FrontEnd.getAvailibilityStats)
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
        

// @LINE:37
case controllers_Assets_versioned20_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_versioned20_invoker.call(controllers.Assets.versioned(path, file))
   }
}
        
}

}
     