// @SOURCE:/Volumes/Work/projects/pirackers/server/conf/routes
// @HASH:ac2cd8aadff840e18035805ff8ec728d7e051e41
// @DATE:Thu Oct 31 15:24:22 CET 2013

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:11
// @LINE:8
// @LINE:6
package controllers {

// @LINE:11
class ReverseAssets {
    

// @LINE:11
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:8
// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:8
def letter(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "letter")
}
                                                
    
}
                          
}
                  


// @LINE:11
// @LINE:8
// @LINE:6
package controllers.javascript {

// @LINE:11
class ReverseAssets {
    

// @LINE:11
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:8
// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:8
def letter : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.letter",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "letter"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:11
// @LINE:8
// @LINE:6
package controllers.ref {


// @LINE:11
class ReverseAssets {
    

// @LINE:11
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:8
// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:8
def letter(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.letter(), HandlerDef(this, "controllers.Application", "letter", Seq(), "GET", """""", _prefix + """letter""")
)
                      
    
}
                          
}
        
    