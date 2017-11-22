import services.BenchMarkUtils
import models.{Normal, Value}

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  sorting()
  random()
  collectionMap()
  constractSeq()
  callByName()
  valueClass()
  futureTrap()
  mapToCollection()

  // compare seq sorted with sorting
  def sorting(): Unit = {
    import scala.util.{Random, Sorting}
    val NUM = 100000
    val target: Seq[Int] = (1 to NUM).map { _ => Random.nextInt(NUM) }
    measureCompareTime("scala.collection.sorted", () => target.sorted)("scala.util.Sorting", () => Sorting.stableSort(target))
  }

  // compare util.Random with concurrent.forkjoin.ThreadLocalRandom
  def random():Unit = {
    import scala.util.Random
    import java.util.concurrent.ThreadLocalRandom // import scala.concurrent.forkjoin.ThreadLocalRandom (alias of java.util.concurrent.ThreadLocalRandom)
    val NUM = 100000
    measureCompareTime("scala.util.Random", () => Random.nextInt(NUM))("scala.concurrent.forkjoin.ThreadLocalRandom", () => ThreadLocalRandom.current().nextInt(NUM))
  }

  // compare with seq map func with set map func
  def collectionMap():Unit = {
    val N: Int = 10000
    val func2: Int => Int = _ % 2
    val func10: Int => Int = _ % 10
    val seq: Seq[Int] = (1 to N).toSeq
    val set: Set[Int] = (1 to N).toSet
    measureCompareTime("seq map func2(%2)", () => seq map func2)("set map func2(%2)", () => set map func2)
    measureCompareTime("seq map func10(%10)", () => seq map func10)("set map func10(%10)", () => set map func10)
  }

  // Compare Nil join with collection apply
  def constractSeq(): Unit = {
    // Using Nil is faster (Nil is only new)
    measureCompareTime("Seq apply (Seq[Int] = Seq(1))", () => Seq(1))("Seq[Int] = 1 :: Nil", () => 1 :: Nil)
    measureCompareTime("List apply (Seq[Int] = List(1))", () => List(1))("Seq[Int] = 1 :: Nil", () => 1 :: Nil)
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
    measureCompareTime("Normal", () => Normal.User(Normal.Id(1L), Normal.Name("hoge")))("Value extend Anyval",() =>  Value.UserValue(Value.IdValue(1L), Value.NameValue("hoge")))
  }

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

  def mapToCollection(): Unit = {
    measureCompareTime("toSet after map", () => (0 to 10000).map(_ + 1).toSet)("use breakout after map", () => (0 to 10000).map(_ + 1)(collection.breakOut): Set[Int])
  }


}