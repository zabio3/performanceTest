import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  sorting()
  random()

  /**
    * seqSort : collectionのsorted
    * Sorting : プリミティブ型に対してのみ有効で、独自型に用いても高速ではない
    */
  def sorting(): Unit = {
    import scala.util.{Random, Sorting}
    val NUM = 100000
    val target: Seq[Int] = (1 to NUM).map { _ => Random.nextInt(NUM) }
    /*
    import models.BenchMarkSet
    val collectionSorted = BenchMarkSet("scala.collection.sorted", () => target.sorted)
    val utilSorted = BenchMarkSet("scala.util.Sorting", () => Sorting.stableSort(target))
    measureCompareTimeA(BenchMarkSet("scala.collection.sorted", () => target.sorted))
    (BenchMarkSet("scala.util.Sorting", () => Sorting.stableSort(target)))
    */
    measureCompareTime("scala.collection.sorted", () => target.sorted)("scala.util.Sorting", () => Sorting.stableSort(target))
  }

  /**
    * scala.util.Random : 
    * scala.concurrent.forkjoin.ThreadLocalRandom :
    */
  def random():Unit = {
    import scala.util.Random
    import scala.concurrent.forkjoin.ThreadLocalRandom
    val NUM = 100000
    measureCompareTime("scala.util.Random", () => Random.nextInt(NUM))("scala.concurrent.forkjoin.ThreadLocalRandom", () => ThreadLocalRandom.current().nextInt(NUM))
  }
}