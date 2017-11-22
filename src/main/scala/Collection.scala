import services.BenchMarkUtils

object Collection {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  // compare seq sorted with sorting
  def sorting(): Unit = {
    import scala.util.{Random, Sorting}
    val NUM = 100000
    val target: Seq[Int] = (1 to NUM).map { _ => Random.nextInt(NUM) }
    measureCompareTime("scala.collection.sorted", () => target.sorted)("scala.util.Sorting", () => Sorting.stableSort(target))
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

  // compare Nil join with collection apply
  def constractSeq(): Unit = {
    // Using Nil is faster (Nil is only new)
    measureCompareTime("Seq apply (Seq[Int] = Seq(1))", () => Seq(1))("Seq[Int] = 1 :: Nil", () => 1 :: Nil)
    measureCompareTime("List apply (Seq[Int] = List(1))", () => List(1))("Seq[Int] = 1 :: Nil", () => 1 :: Nil)
  }

  def mapToCollection(): Unit = {
    measureCompareTime("toSet after map", () => (0 to 10000).map(_ + 1).toSet)("use breakout after map", () => (0 to 10000).map(_ + 1)(collection.breakOut): Set[Int])
  }

  def loop(): Unit = {
    var acc = 0.0
    val loopForFunc = () => for (i <- 0 until 100) acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    val loopFoldLeftFunc = () => (0 until 100).foldLeft(0.0)({ (d, i) => d + 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)})
    measureCompareTime("for loop", () => loopForFunc())("foldLeft loop", () => loopFoldLeftFunc())
  }

}