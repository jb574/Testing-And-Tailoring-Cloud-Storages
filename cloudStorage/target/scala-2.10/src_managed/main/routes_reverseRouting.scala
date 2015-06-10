// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:a681126f2b19307a73c1bcf0cb987b2d59e790d3
// @DATE:Wed Jun 10 14:14:59 BST 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString


// @LINE:12
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:12
class ReverseAssets {


// @LINE:12
def versioned(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:8
def saveQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "addQuery")
}
                        

// @LINE:7
def listQueries(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "seenQueries")
}
                        

// @LINE:6
def testAction(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "test")
}
                        

// @LINE:9
def saveMutableQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "updateData")
}
                        

}
                          
}
                  


// @LINE:12
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:12
class ReverseAssets {


// @LINE:12
def versioned : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.versioned",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:8
def saveQuery : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.saveQuery",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addQuery"})
      }
   """
)
                        

// @LINE:7
def listQueries : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.listQueries",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "seenQueries"})
      }
   """
)
                        

// @LINE:6
def testAction : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.testAction",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "test"})
      }
   """
)
                        

// @LINE:9
def saveMutableQuery : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.saveMutableQuery",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "updateData"})
      }
   """
)
                        

}
              
}
        


// @LINE:12
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:12
class ReverseAssets {


// @LINE:12
def versioned(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.versioned(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {


// @LINE:8
def saveQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveQuery", Seq(), "POST", """""", _prefix + """addQuery""")
)
                      

// @LINE:7
def listQueries(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.listQueries(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listQueries", Seq(), "GET", """""", _prefix + """seenQueries""")
)
                      

// @LINE:6
def testAction(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.testAction(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "testAction", Seq(), "GET", """ Home page""", _prefix + """test""")
)
                      

// @LINE:9
def saveMutableQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveMutableQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveMutableQuery", Seq(), "POST", """""", _prefix + """updateData""")
)
                      

}
                          
}
        
    