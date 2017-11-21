package services

import scala.math.pow

trait UnitCalcable {

  val Giga = pow(1000, 3)
  private val Mega = pow(1000, 2)
  private val Kilo = pow(1000, 1)
  private val Mill = pow(1000, -1)
  private val Micro = pow(1000, -2)
  private val Nano = pow(1000, -3)

  def convertUnit(x:Double):(Double, String) = {
    x match {
      case s if s >= Giga => (s / Giga, "ギガ")
      case s if s >= Mega => (s / Mega, "メガ")
      case s if s >= Kilo => (s / Kilo, "キロ")
      case s if s >= Mill => (s / Mill, "ミリ")
      case s if s >= Micro => (s / Micro, "マイクロ")
      case s if s >= Nano => (s / Nano, "ナノ")
      case _ => (x, "")
    }
  }

}