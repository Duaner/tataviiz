package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

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
    services.Serial.send(h.toByte.toString)
    Ok
  }

}
