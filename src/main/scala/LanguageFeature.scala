import services.BenchMarkUtils
import models.{Normal, Value}

object LanguageFeature {
  val measureCompareTime = BenchMarkUtils.compareMeasure _

  def futureTrap(): Unit = {
    import Thread.sleep
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    val result: Future[Int] = for {
      a <- Future { sleep(100); println("finish 100"); 100}
      b <- Future { sleep(50); println("finish 50"); 50}
    } yield a + b

    val aF = Future { sleep(100); println("finish 100"); 100}
    val bF = Future { sleep(50); println("finish 50"); 50}

    val result2: Future[Int] = for {
      a <- aF
      b <- bF
    } yield a + b

    measureCompareTime("inside future", () => result.onComplete(println))("outside future", () => result2.onComplete(println))
  }


  // compare call-by-name with ordinary arguments
  def callByName(): Unit = {
    measureCompareTime("byName(false)", () => byName(() => (1 to 100000).toString(),false))("byValue(false)",() => byValue((1 to 100000).toString(),false))
    measureCompareTime("byName(true)", () => byName(() => (1 to 100000).toString(),true))("byValue(true)",() => byValue((1 to 100000).toString(),true))
  }

  private def byName(value: () => String,flag: Boolean): String = if (flag) value() else ""
  private def byValue(value: String,flag: Boolean): String = if (flag) value else ""

  // value class instance compare extend AnyVal with ordinary value
  def valueClass(): Unit = {
    val normalFunc = () => Normal.User(Normal.Id(1L), Normal.Name("hoge"))
    val extendAnyValFunc = () =>  Value.UserValue(Value.IdValue(1L), Value.NameValue("hoge"))
    measureCompareTime("Normal", normalFunc)("Value extend Anyval", extendAnyValFunc)
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