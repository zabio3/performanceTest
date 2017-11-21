object BenchMark extends App {

  val measureTime = BenchMarkUtils.measure _
  val measureCompareTime = BenchMarkUtils.compareMeasure _

  /**
    * Sortテスト
    * seqSort :
    * Sorting : プリミティブ型に対してのみ有効で、独自型に用いても高速ではない
    */
  import scala.util.Random
  import scala.util.Sorting
  val NUM = 10000
  val target: Seq[Int] = (1 to NUM).map { _ => Random.nextInt(NUM) }

  measureCompareTime("scala.collection.sorted", target.sorted)("scala.util.Sorting", Sorting.stableSort(target))

}