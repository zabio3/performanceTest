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

  def regularExpression(): Unit = {
    import scala.language.postfixOps
    val data = "aeajfcsUw+OHopAduwbUbudiIjioewA0jw+varw"
    val re = """(?<=\w+)/.+""" r
    val ms = re findAllIn data
    val xsFunc = () => if (ms.isEmpty) None else Some(ms.next())
    val re2 = """\w+/""" r
    val ysFunc = () => re2 findPrefixMatchOf data map(_.after.toString)
    println(xsFunc(), ysFunc())
    measureCompareTime("findAllIn and positive after read", () => (0 to 100).foreach(_ => xsFunc()))("findPrefixOf ", () => (0 to 100).foreach((_ => ysFunc())))
  }

}