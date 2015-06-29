// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:11b3fd8728f8cac56b6a82c10785b04a59df62ee
// @DATE:Mon Jun 29 10:38:02 BST 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString


// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:19
class ReverseAssets {


// @LINE:19
def versioned(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
class ReverseFrontEnd {


// @LINE:13
def getLogOutput(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "log")
}
                        

// @LINE:16
def makeConsistent(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "makeConsistent")
}
                        

// @LINE:15
def delete(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "delete")
}
                        

// @LINE:20
def runEventuallyConsistentQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "select")
}
                        

// @LINE:17
def changeTImeSweep(updatedTime:Int): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "time/updatedTIme" + queryString(List(Some(implicitly[QueryStringBindable[Int]].unbind("updatedTime", updatedTime)))))
}
                        

// @LINE:14
def insert(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "insert")
}
                        

// @LINE:11
def dropTable(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "dropTable")
}
                        

// @LINE:10
def createTable(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "createTable")
}
                        

// @LINE:12
def update(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "updateData")
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
   Call("POST", _prefix + { _defaultPrefix } + "updateDataOld")
}
                        

}
                          
}
                  


// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:19
class ReverseAssets {


// @LINE:19
def versioned : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.versioned",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
class ReverseFrontEnd {


// @LINE:13
def getLogOutput : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.getLogOutput",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "log"})
      }
   """
)
                        

// @LINE:16
def makeConsistent : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.makeConsistent",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "makeConsistent"})
      }
   """
)
                        

// @LINE:15
def delete : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.delete",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "delete"})
      }
   """
)
                        

// @LINE:20
def runEventuallyConsistentQuery : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.runEventuallyConsistentQuery",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "select"})
      }
   """
)
                        

// @LINE:17
def changeTImeSweep : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.changeTImeSweep",
   """
      function(updatedTime) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "time/updatedTIme" + _qS([(""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("updatedTime", updatedTime)])})
      }
   """
)
                        

// @LINE:14
def insert : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.insert",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "insert"})
      }
   """
)
                        

// @LINE:11
def dropTable : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.dropTable",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "dropTable"})
      }
   """
)
                        

// @LINE:10
def createTable : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.createTable",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "createTable"})
      }
   """
)
                        

// @LINE:12
def update : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.update",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "updateData"})
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
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "updateDataOld"})
      }
   """
)
                        

}
              
}
        


// @LINE:20
// @LINE:19
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:19
class ReverseAssets {


// @LINE:19
def versioned(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.versioned(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:20
// @LINE:17
// @LINE:16
// @LINE:15
// @LINE:14
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
class ReverseFrontEnd {


// @LINE:13
def getLogOutput(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.getLogOutput(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getLogOutput", Seq(), "GET", """""", _prefix + """log""")
)
                      

// @LINE:16
def makeConsistent(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.makeConsistent(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "makeConsistent", Seq(), "GET", """""", _prefix + """makeConsistent""")
)
                      

// @LINE:15
def delete(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.delete(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "delete", Seq(), "POST", """""", _prefix + """delete""")
)
                      

// @LINE:20
def runEventuallyConsistentQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.runEventuallyConsistentQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "runEventuallyConsistentQuery", Seq(), "POST", """""", _prefix + """select""")
)
                      

// @LINE:17
def changeTImeSweep(updatedTime:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.changeTImeSweep(updatedTime), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "changeTImeSweep", Seq(classOf[Int]), "POST", """""", _prefix + """time/updatedTIme""")
)
                      

// @LINE:14
def insert(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.insert(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "insert", Seq(), "POST", """""", _prefix + """insert""")
)
                      

// @LINE:11
def dropTable(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.dropTable(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "dropTable", Seq(), "POST", """""", _prefix + """dropTable""")
)
                      

// @LINE:10
def createTable(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.createTable(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "createTable", Seq(), "POST", """""", _prefix + """createTable""")
)
                      

// @LINE:12
def update(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.update(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "update", Seq(), "POST", """""", _prefix + """updateData""")
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
   controllers.Application.saveMutableQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveMutableQuery", Seq(), "POST", """""", _prefix + """updateDataOld""")
)
                      

}
                          
}
        
    