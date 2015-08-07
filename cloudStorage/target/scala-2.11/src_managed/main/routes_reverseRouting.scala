// @SOURCE:/Users/jackdavey/Documents/Testing-And-Tailoring-Cloud-Storages/cloudStorage/conf/routes
// @HASH:0d2a8f59b5a3e0957af1e51a4770dca3e2822945
// @DATE:Wed Aug 05 14:18:10 BST 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString


// @LINE:37
// @LINE:34
// @LINE:33
// @LINE:32
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
package controllers {

// @LINE:34
// @LINE:33
// @LINE:32
// @LINE:29
class ReverseSampleApplications {


// @LINE:34
// @LINE:29
def amazonTest(): Call = {
   () match {
// @LINE:29
case ()  =>
  import ReverseRouteContext.empty
  Call("GET", _prefix + { _defaultPrefix } + "amazonTest")
                                         
   }
}
                                                

// @LINE:33
def runSecondSample(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "sample2")
}
                        

// @LINE:32
def runFirstSample(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "sample1")
}
                        

}
                          

// @LINE:37
class ReverseAssets {


// @LINE:37
def versioned(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:30
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
class ReverseFrontEnd {


// @LINE:21
def getLogOutput(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "log")
}
                        

// @LINE:24
def makeConsistent(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "makeConsistent")
}
                        

// @LINE:23
def delete(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "delete")
}
                        

// @LINE:30
def getAvailibilityStats(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "stats")
}
                        

// @LINE:27
def runEventuallyConsistentQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "select")
}
                        

// @LINE:28
def getAllPossibilities(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "selectAllPossible")
}
                        

// @LINE:26
def getInconsistentUPdateInfo(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "allUpdates")
}
                        

// @LINE:22
def insert(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "insert")
}
                        

// @LINE:25
def changeProperty(name:String, value:Int): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "updateVal/name/value" + queryString(List(Some(implicitly[QueryStringBindable[String]].unbind("name", name)), Some(implicitly[QueryStringBindable[Int]].unbind("value", value)))))
}
                        

// @LINE:19
def dropTable(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "dropTable")
}
                        

// @LINE:18
def createTable(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "createTable")
}
                        

// @LINE:20
def update(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "updateData")
}
                        

}
                          

// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
class ReverseApplication {


// @LINE:11
def saveQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "addQuery")
}
                        

// @LINE:10
def listQueries(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "seenQueries")
}
                        

// @LINE:9
def testAction(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "testold")
}
                        

// @LINE:12
def saveMutableQuery(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "updateDataOld")
}
                        

}
                          
}
                  


// @LINE:37
// @LINE:34
// @LINE:33
// @LINE:32
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:34
// @LINE:33
// @LINE:32
// @LINE:29
class ReverseSampleApplications {


// @LINE:34
// @LINE:29
def amazonTest : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.SampleApplications.amazonTest",
   """
      function() {
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "amazonTest"})
      }
      if (true) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "test"})
      }
      }
   """
)
                        

// @LINE:33
def runSecondSample : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.SampleApplications.runSecondSample",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "sample2"})
      }
   """
)
                        

// @LINE:32
def runFirstSample : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.SampleApplications.runFirstSample",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "sample1"})
      }
   """
)
                        

}
              

// @LINE:37
class ReverseAssets {


// @LINE:37
def versioned : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.versioned",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:30
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
class ReverseFrontEnd {


// @LINE:21
def getLogOutput : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.getLogOutput",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "log"})
      }
   """
)
                        

// @LINE:24
def makeConsistent : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.makeConsistent",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "makeConsistent"})
      }
   """
)
                        

// @LINE:23
def delete : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.delete",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "delete"})
      }
   """
)
                        

// @LINE:30
def getAvailibilityStats : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.getAvailibilityStats",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "stats"})
      }
   """
)
                        

// @LINE:27
def runEventuallyConsistentQuery : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.runEventuallyConsistentQuery",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "select"})
      }
   """
)
                        

// @LINE:28
def getAllPossibilities : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.getAllPossibilities",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "selectAllPossible"})
      }
   """
)
                        

// @LINE:26
def getInconsistentUPdateInfo : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.getInconsistentUPdateInfo",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "allUpdates"})
      }
   """
)
                        

// @LINE:22
def insert : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.insert",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "insert"})
      }
   """
)
                        

// @LINE:25
def changeProperty : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.changeProperty",
   """
      function(name,value) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "updateVal/name/value" + _qS([(""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("name", name), (""" + implicitly[QueryStringBindable[Int]].javascriptUnbind + """)("value", value)])})
      }
   """
)
                        

// @LINE:19
def dropTable : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.dropTable",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "dropTable"})
      }
   """
)
                        

// @LINE:18
def createTable : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.createTable",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "createTable"})
      }
   """
)
                        

// @LINE:20
def update : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FrontEnd.update",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "updateData"})
      }
   """
)
                        

}
              

// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
class ReverseApplication {


// @LINE:11
def saveQuery : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.saveQuery",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addQuery"})
      }
   """
)
                        

// @LINE:10
def listQueries : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.listQueries",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "seenQueries"})
      }
   """
)
                        

// @LINE:9
def testAction : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.testAction",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "testold"})
      }
   """
)
                        

// @LINE:12
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
        


// @LINE:37
// @LINE:34
// @LINE:33
// @LINE:32
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
package controllers.ref {


// @LINE:34
// @LINE:33
// @LINE:32
// @LINE:29
class ReverseSampleApplications {


// @LINE:29
def amazonTest(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.SampleApplications.amazonTest(), HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "amazonTest", Seq(), "GET", """""", _prefix + """amazonTest""")
)
                      

// @LINE:33
def runSecondSample(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.SampleApplications.runSecondSample(), HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "runSecondSample", Seq(), "GET", """""", _prefix + """sample2""")
)
                      

// @LINE:32
def runFirstSample(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.SampleApplications.runFirstSample(), HandlerDef(this.getClass.getClassLoader, "", "controllers.SampleApplications", "runFirstSample", Seq(), "GET", """ the urls below show the ample applications i wrote to help rove my eventual consistency implementation does the jo""", _prefix + """sample1""")
)
                      

}
                          

// @LINE:37
class ReverseAssets {


// @LINE:37
def versioned(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.versioned(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "versioned", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      

}
                          

// @LINE:30
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:23
// @LINE:22
// @LINE:21
// @LINE:20
// @LINE:19
// @LINE:18
class ReverseFrontEnd {


// @LINE:21
def getLogOutput(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.getLogOutput(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getLogOutput", Seq(), "GET", """""", _prefix + """log""")
)
                      

// @LINE:24
def makeConsistent(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.makeConsistent(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "makeConsistent", Seq(), "GET", """""", _prefix + """makeConsistent""")
)
                      

// @LINE:23
def delete(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.delete(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "delete", Seq(), "POST", """""", _prefix + """delete""")
)
                      

// @LINE:30
def getAvailibilityStats(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.getAvailibilityStats(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getAvailibilityStats", Seq(), "GET", """""", _prefix + """stats""")
)
                      

// @LINE:27
def runEventuallyConsistentQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.runEventuallyConsistentQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "runEventuallyConsistentQuery", Seq(), "POST", """""", _prefix + """select""")
)
                      

// @LINE:28
def getAllPossibilities(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.getAllPossibilities(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getAllPossibilities", Seq(), "POST", """""", _prefix + """selectAllPossible""")
)
                      

// @LINE:26
def getInconsistentUPdateInfo(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.getInconsistentUPdateInfo(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "getInconsistentUPdateInfo", Seq(), "GET", """""", _prefix + """allUpdates""")
)
                      

// @LINE:22
def insert(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.insert(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "insert", Seq(), "POST", """""", _prefix + """insert""")
)
                      

// @LINE:25
def changeProperty(name:String, value:Int): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.changeProperty(name, value), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "changeProperty", Seq(classOf[String], classOf[Int]), "POST", """""", _prefix + """updateVal/name/value""")
)
                      

// @LINE:19
def dropTable(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.dropTable(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "dropTable", Seq(), "POST", """""", _prefix + """dropTable""")
)
                      

// @LINE:18
def createTable(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.createTable(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "createTable", Seq(), "POST", """These urls  are the services that form
 my implementation of eventual consistency""", _prefix + """createTable""")
)
                      

// @LINE:20
def update(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FrontEnd.update(), HandlerDef(this.getClass.getClassLoader, "", "controllers.FrontEnd", "update", Seq(), "POST", """""", _prefix + """updateData""")
)
                      

}
                          

// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
class ReverseApplication {


// @LINE:11
def saveQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveQuery", Seq(), "POST", """""", _prefix + """addQuery""")
)
                      

// @LINE:10
def listQueries(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.listQueries(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "listQueries", Seq(), "GET", """""", _prefix + """seenQueries""")
)
                      

// @LINE:9
def testAction(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.testAction(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "testAction", Seq(), "GET", """these urls are for the very simple pieces of code
 i developed as part of the first tieration
 they dont contribute anythign useful to the
rest of the application""", _prefix + """testold""")
)
                      

// @LINE:12
def saveMutableQuery(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.saveMutableQuery(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "saveMutableQuery", Seq(), "POST", """""", _prefix + """updateDataOld""")
)
                      

}
                          
}
        
    