import cats.{Now, Eval}

import cats.data._
import cats.implicits._

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

  // たらい回し関数のチェック
  def tarai(x: Int, y: Int, z: Int): Int =
    if (x <= y) y else tarai(tarai(x - 1, y, z),
      tarai(y - 1, z, x),
      tarai(z - 1, x, y))

  def taraiE(x: Eval[Int], y: Eval[Int], z: Eval[Int]): Eval[Int] =
    (x, y).mapN(_ <= _) ifM (y, taraiE(taraiE(x.map(_ - 1), y, z),
      taraiE(y.map(_ - 1), z, x),
      taraiE(z.map(_ - 1), x, y)))

  def taraiFunc(): Unit = {
    // tarai(20, 10, 5)
    measureCompareTime("tarai func ", () => tarai(20, 10, 5))("taraiE  ", () => taraiE(Now(20), Now(20), Now(5)).value )
  }


}