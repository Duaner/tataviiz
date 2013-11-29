package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.iteratee._
import concurrent._
import concurrent.ExecutionContext.Implicits.global

import utils._

object Application extends Controller {
  def index = Action {
    Ok(views.html.index())
  }

  def height = Action(parse.json) { request =>
    println(request.body)
    val h: BigDecimal = request.body \ "height" match {
      case JsNumber(value) => value
      case _ => BigDecimal(0)
    }
    services.Serial.send(h.toByte)
    Ok
  }
  
  def heightWS = WebSocket.async[JsValue] { request =>
    Future {
      val out = Enumerator[JsValue]()
      val in = Iteratee.foreach[JsValue](_ match {
        case JsNumber(value) => {
          services.Serial.send(value.toByte)
        }
      }) mapDone { _ => 
        services.Serial.send(0.toByte)
      }
      (in, out)
    }
  }

}
