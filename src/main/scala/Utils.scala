import services.BenchMarkUtils

object Util {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  // compare util.Random with concurrent.forkjoin.ThreadLocalRandom
  def random():Unit = {
    import scala.util.Random
    import java.util.concurrent.ThreadLocalRandom // import scala.concurrent.forkjoin.ThreadLocalRandom (alias of java.util.concurrent.ThreadLocalRandom)
    val NUM = 100000
    measureCompareTime("scala.util.Random", () => Random.nextInt(NUM))("scala.concurrent.forkjoin.ThreadLocalRandom", () => ThreadLocalRandom.current().nextInt(NUM))
  }

}