import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  sorting()
  random()
  collectionMap()

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
    import java.util.concurrent.ThreadLocalRandom // import scala.concurrent.forkjoin.ThreadLocalRandom (java.util.concurrent.ThreadLocalRandom のエイリアス)
    val NUM = 100000
    measureCompareTime("scala.util.Random", () => Random.nextInt(NUM))("scala.concurrent.forkjoin.ThreadLocalRandom", () => ThreadLocalRandom.current().nextInt(NUM))
  }

  def collectionMap():Unit = {
    val N: Int = 10000
    val func2: Int => Int = _ % 2
    val func10: Int => Int = _ % 10
    val seq: Seq[Int] = (1 to N).toSeq
    val set: Set[Int] = (1 to N).toSet
    measureCompareTime("seq map func2(%2)", () => seq map func2)("set map func2(%2)", () => set map func2)
    measureCompareTime("seq map func10(%10)", () => seq map func10)("set map func10(%10)", () => set map func10)
  }
}