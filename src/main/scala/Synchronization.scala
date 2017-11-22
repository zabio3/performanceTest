import services.BenchMarkUtils

object Synchronization {
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

}