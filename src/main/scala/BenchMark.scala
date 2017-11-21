import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  /**
    * Sortテスト
    * seqSort :
    * Sorting : プリミティブ型に対してのみ有効で、独自型に用いても高速ではない
    */
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