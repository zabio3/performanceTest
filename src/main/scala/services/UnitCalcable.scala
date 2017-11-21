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
    val isPositive = if (x >= 0) true else false
    val mod = absoluteValue(isPositive) _

    mod(x) match {
      case s if s >= Giga => (mod(s / Giga), "ギガ")
      case s if s >= Mega => (mod(s / Mega), "メガ")
      case s if s >= Kilo => (mod(s / Kilo), "キロ")
      case s if s >= Mill => (mod(s / Mill), "ミリ")
      case s if s >= Micro => (mod(s / Micro), "マイクロ")
      case s if s >= Nano => (mod(s / Nano), "ナノ")
      case _ => (mod(x), "")
    }
  }

  private def absoluteValue(isPositive:Boolean)(x:Double):Double = if (isPositive) x else -x
}