// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:d784cf765f3a8dff25b9dc8c393f0d58a766e957
// @DATE:Mon Jun 08 13:46:37 BST 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString


// @LINE:13
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:13
class ReverseAssets {


// @LINE:13
def versioned(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:6
def listBooks(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "books")
}
                        

// @LINE:7
def saveBook(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "saveBook")
}
                        

// @LINE:8
def testAction(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "test")
}
                        

// @LINE:10
def saveQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "addQuery")
}
                        

// @LINE:9
def listQueries(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "seenQueries")
}
                        

}
                          
}
                  


// @LINE:13
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:13
class ReverseAssets {


// @LINE:13
def versioned : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.versioned",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:6
def listBooks : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.listBooks",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "books"})
      }
   """
)
                        

// @LINE:7
def saveBook : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.saveBook",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "saveBook"})
      }
   """
)
                        

// @LINE:8
def testAction : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.testAction",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "test"})
      }
   """
)
                        

// @LINE:10
def saveQuery : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.saveQuery",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addQuery"})
      }
   """
)
                        

// @LINE:9
def listQueries : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.listQueries",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "seenQueries"})
      }
   """
)
                        

}
              
}
        


// @LINE:13
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:13
class ReverseAssets {


// @LINE:13
def versioned(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.versioned(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:6
def listBooks(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.listBooks(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listBooks", Seq(), "GET", """ Home page""", _prefix + """books""")
)
                      

// @LINE:7
def saveBook(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveBook(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveBook", Seq(), "POST", """""", _prefix + """saveBook""")
)
                      

// @LINE:8
def testAction(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.testAction(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "testAction", Seq(), "GET", """""", _prefix + """test""")
)
                      

// @LINE:10
def saveQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveQuery", Seq(), "POST", """""", _prefix + """addQuery""")
)
                      

// @LINE:9
def listQueries(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.listQueries(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listQueries", Seq(), "GET", """""", _prefix + """seenQueries""")
)
                      

}
                          
}
        
    