import services.BenchMarkUtils

object Util {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  // compare util.Random with concurrent.forkjoin.ThreadLocalRandom
  def random():Unit = {
    import scala.util.Random
    import java.util.concurrent.ThreadLocalRandom // import scala.concurrent.forkjoin.ThreadLocalRandom (alias of java.util.concurrent.ThreadLocalRandom)
    val NUM = 10000
    val utilRandomFunc = () => Random.nextInt(NUM)
    val threadLocalRandomFunc = () => ThreadLocalRandom.current().nextInt(NUM)
    measureCompareTime("scala.util.Random", utilRandomFunc)("scala.concurrent.forkjoin.ThreadLocalRandom", threadLocalRandomFunc)
  }

}