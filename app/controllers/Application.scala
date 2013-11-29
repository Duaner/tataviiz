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

    val cl = h.toString match {
      case "a" => "a"
      case "e" => "e"
      case "i" => "i"
      case "o" => "o"
      case "u" => "u"
      case "œ" => "e"
      case _ => " "
    }

    services.Serial.send(cl);
    Ok(cl)
  }

}