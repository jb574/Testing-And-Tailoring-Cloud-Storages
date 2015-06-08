// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:d784cf765f3a8dff25b9dc8c393f0d58a766e957
// @DATE:Mon Jun 08 13:46:37 BST 2015


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
private[this] lazy val controllers_Application_listBooks0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("books"))))
private[this] lazy val controllers_Application_listBooks0_invoker = createInvoker(
controllers.Application.listBooks,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listBooks", Nil,"GET", """ Home page""", Routes.prefix + """books"""))
        

// @LINE:7
private[this] lazy val controllers_Application_saveBook1_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("saveBook"))))
private[this] lazy val controllers_Application_saveBook1_invoker = createInvoker(
controllers.Application.saveBook,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveBook", Nil,"POST", """""", Routes.prefix + """saveBook"""))
        

// @LINE:8
private[this] lazy val controllers_Application_testAction2_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("test"))))
private[this] lazy val controllers_Application_testAction2_invoker = createInvoker(
controllers.Application.testAction,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "testAction", Nil,"GET", """""", Routes.prefix + """test"""))
        

// @LINE:9
private[this] lazy val controllers_Application_listQueries3_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("seenQueries"))))
private[this] lazy val controllers_Application_listQueries3_invoker = createInvoker(
controllers.Application.listQueries,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listQueries", Nil,"GET", """""", Routes.prefix + """seenQueries"""))
        

// @LINE:10
private[this] lazy val controllers_Application_saveQuery4_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("addQuery"))))
private[this] lazy val controllers_Application_saveQuery4_invoker = createInvoker(
controllers.Application.saveQuery,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveQuery", Nil,"POST", """""", Routes.prefix + """addQuery"""))
        

// @LINE:13
private[this] lazy val controllers_Assets_versioned5_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_versioned5_invoker = createInvoker(
controllers.Assets.versioned(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
        
def documentation = List(("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """books""","""controllers.Application.listBooks"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """saveBook""","""controllers.Application.saveBook"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """test""","""controllers.Application.testAction"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """seenQueries""","""controllers.Application.listQueries"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """addQuery""","""controllers.Application.saveQuery"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.versioned(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_listBooks0_route(params) => {
   call { 
        controllers_Application_listBooks0_invoker.call(controllers.Application.listBooks)
   }
}
        

// @LINE:7
case controllers_Application_saveBook1_route(params) => {
   call { 
        controllers_Application_saveBook1_invoker.call(controllers.Application.saveBook)
   }
}
        

// @LINE:8
case controllers_Application_testAction2_route(params) => {
   call { 
        controllers_Application_testAction2_invoker.call(controllers.Application.testAction)
   }
}
        

// @LINE:9
case controllers_Application_listQueries3_route(params) => {
   call { 
        controllers_Application_listQueries3_invoker.call(controllers.Application.listQueries)
   }
}
        

// @LINE:10
case controllers_Application_saveQuery4_route(params) => {
   call { 
        controllers_Application_saveQuery4_invoker.call(controllers.Application.saveQuery)
   }
}
        

// @LINE:13
case controllers_Assets_versioned5_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_versioned5_invoker.call(controllers.Assets.versioned(path, file))
   }
}
        
}

}
     