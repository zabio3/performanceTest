import services.BenchMarkUtils
import models.{Normal, Value}

object Other {
 val measureCompareTime = BenchMarkUtils.compareMeasure _

  // compare call-by-name with ordinary arguments
  def callByName(): Unit = {
    measureCompareTime("byName(false)", () => byName(() => (1 to 100000).toString(),false))("byValue(false)",() => byValue((1 to 100000).toString(),false))
    measureCompareTime("byName(true)", () => byName(() => (1 to 100000).toString(),true))("byValue(true)",() => byValue((1 to 100000).toString(),true))
  }

  private def byName(value: () => String,flag: Boolean): String = if (flag) value() else ""
  private def byValue(value: String,flag: Boolean): String = if (flag) value else ""

  // value class instance compare extend AnyVal with ordinary value
  def valueClass(): Unit = {
    measureCompareTime("Normal", () => Normal.User(Normal.Id(1L), Normal.Name("hoge")))("Value extend Anyval",() =>  Value.UserValue(Value.IdValue(1L), Value.NameValue("hoge")))
  }

  def reflectionStructureSubType(): Unit = {
    measureCompareTime("Not Structure Sub Type", () => notReflection(Bar, 1, 2))("Structure Sub Type", () => reflection(Bar, 1, 2))
  }

  object Bar extends CalcOperator
  trait CalcOperator { def add(a:Int, b:Int): Int = a + b}

  private def notReflection(calcOperator: CalcOperator, a:Int, b:Int): Int = { calcOperator.add(a,b) }

  import scala.language.reflectiveCalls
  private def reflection(obj: {def add(a:Int, b:Int): Int}, a:Int, b:Int):Int = { obj.add(a,b) }

}