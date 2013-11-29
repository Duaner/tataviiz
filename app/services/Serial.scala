package services

import utils._

object Serial {
  val serial = new SerialOverUSB();

  def initialize = {
    serial.initialize
  }

  def send(s: String) = {
    serial.send(s.getBytes("US-ASCII")(0))
  }
}