// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:0b37644acc9202bfbcc8c2cb8a3aac7538cfda77
// @DATE:Wed Jun 17 12:34:58 BST 2015


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


// @LINE:6
private[this] lazy val controllers_Application_testAction0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("test"))))
private[this] lazy val controllers_Application_testAction0_invoker = createInvoker(
controllers.Application.testAction,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "testAction", Nil,"GET", """ Home page""", Routes.prefix + """test"""))
        

// @LINE:7
private[this] lazy val controllers_Application_listQueries1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("seenQueries"))))
private[this] lazy val controllers_Application_listQueries1_invoker = createInvoker(
controllers.Application.listQueries,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listQueries", Nil,"GET", """""", Routes.prefix + """seenQueries"""))
        

// @LINE:8
private[this] lazy val controllers_Application_saveQuery2_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addQuery"))))
private[this] lazy val controllers_Application_saveQuery2_invoker = createInvoker(
controllers.Application.saveQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveQuery", Nil,"POST", """""", Routes.prefix + """addQuery"""))
        

// @LINE:9
private[this] lazy val controllers_Application_saveMutableQuery3_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateDataOld"))))
private[this] lazy val controllers_Application_saveMutableQuery3_invoker = createInvoker(
controllers.Application.saveMutableQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveMutableQuery", Nil,"POST", """""", Routes.prefix + """updateDataOld"""))
        

// @LINE:10
private[this] lazy val controllers_FrontEnd_createTable4_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("createTable"))))
private[this] lazy val controllers_FrontEnd_createTable4_invoker = createInvoker(
controllers.FrontEnd.createTable,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "createTable", Nil,"POST", """""", Routes.prefix + """createTable"""))
        

// @LINE:11
private[this] lazy val controllers_FrontEnd_dropTable5_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("dropTable"))))
private[this] lazy val controllers_FrontEnd_dropTable5_invoker = createInvoker(
controllers.FrontEnd.dropTable,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "dropTable", Nil,"POST", """""", Routes.prefix + """dropTable"""))
        

// @LINE:12
private[this] lazy val controllers_FrontEnd_update6_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("updateData"))))
private[this] lazy val controllers_FrontEnd_update6_invoker = createInvoker(
controllers.FrontEnd.update,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "update", Nil,"POST", """""", Routes.prefix + """updateData"""))
        

// @LINE:13
private[this] lazy val controllers_FrontEnd_getLogOutput7_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("log"))))
private[this] lazy val controllers_FrontEnd_getLogOutput7_invoker = createInvoker(
controllers.FrontEnd.getLogOutput,
HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getLogOutput", Nil,"GET", """""", Routes.prefix + """log"""))
        

// @LINE:15
private[this] lazy val controllers_Assets_versioned8_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_versioned8_invoker = createInvoker(
controllers.Assets.versioned(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test""","""controllers.Application.testAction"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """seenQueries""","""controllers.Application.listQueries"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addQuery""","""controllers.Application.saveQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateDataOld""","""controllers.Application.saveMutableQuery"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """createTable""","""controllers.FrontEnd.createTable"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """dropTable""","""controllers.FrontEnd.dropTable"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """updateData""","""controllers.FrontEnd.update"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """log""","""controllers.FrontEnd.getLogOutput"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.versioned(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_testAction0_route(params) => {
   call { 
        controllers_Application_testAction0_invoker.call(controllers.Application.testAction)
   }
}
        

// @LINE:7
case controllers_Application_listQueries1_route(params) => {
   call { 
        controllers_Application_listQueries1_invoker.call(controllers.Application.listQueries)
   }
}
        

// @LINE:8
case controllers_Application_saveQuery2_route(params) => {
   call { 
        controllers_Application_saveQuery2_invoker.call(controllers.Application.saveQuery)
   }
}
        

// @LINE:9
case controllers_Application_saveMutableQuery3_route(params) => {
   call { 
        controllers_Application_saveMutableQuery3_invoker.call(controllers.Application.saveMutableQuery)
   }
}
        

// @LINE:10
case controllers_FrontEnd_createTable4_route(params) => {
   call { 
        controllers_FrontEnd_createTable4_invoker.call(controllers.FrontEnd.createTable)
   }
}
        

// @LINE:11
case controllers_FrontEnd_dropTable5_route(params) => {
   call { 
        controllers_FrontEnd_dropTable5_invoker.call(controllers.FrontEnd.dropTable)
   }
}
        

// @LINE:12
case controllers_FrontEnd_update6_route(params) => {
   call { 
        controllers_FrontEnd_update6_invoker.call(controllers.FrontEnd.update)
   }
}
        

// @LINE:13
case controllers_FrontEnd_getLogOutput7_route(params) => {
   call { 
        controllers_FrontEnd_getLogOutput7_invoker.call(controllers.FrontEnd.getLogOutput)
   }
}
        

// @LINE:15
case controllers_Assets_versioned8_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_versioned8_invoker.call(controllers.Assets.versioned(path, file))
   }
}
        
}

}
     