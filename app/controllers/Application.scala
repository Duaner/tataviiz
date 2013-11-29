package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import utils._

object Application extends Controller {
  def index = Action {
    Ok(views.html.index(""))
  }

  def letter = Action { request =>
    val l: String = request.getQueryString("q").getOrElse(" ")

    val cl = l match {
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