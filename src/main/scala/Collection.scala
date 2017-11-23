import services.BenchMarkUtils

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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
    val toSetFunc = () => (0 to 10000).map(_ + 1).toSet
    val usingBreakOutFunc = () => (0 to 10000).map(_ + 1)(collection.breakOut): Set[Int]
    measureCompareTime("toSet after map", toSetFunc)("use breakout after map", usingBreakOutFunc)
  }

  def loop(): Unit = {
    var acc = 0.0
    val loopForFunc = () => for (i <- 0 until 100) acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    val loopFoldLeftFunc = () => (0 until 100).foldLeft(0.0)({ (d, i) => d + 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)})
    measureCompareTime("for loop", loopForFunc)("foldLeft loop", loopFoldLeftFunc)
  }

  def iteratorCompareStream(): Unit = {
    (1 to 10000).toIterator
    (1 to 10000).toStream
    measureCompareTime("Iterator", () => (1 to 10000).toIterator)("Stream", () => (1 to 10000).toStream)
  }

  def streamCompare(): Unit = {
    // 1000L,100000000000L
    val streamFunc = () => Stream.range(1, 1000000L) take(1000000)
    val listFunc = () => List.range(1, 1000000L) take(1000000)
    measureCompareTime("streamFunc", streamFunc)("listFunc ", listFunc)
  }

  def addingTail(): Unit = {
    var xs = Vector.empty[Int]
    val ys = ArrayBuffer.empty[Int]
    val xsFunc = () => (1 to 10000) foreach {n => xs = xs :+ n}
    val ysFunc = () => (1 to 10000) foreach {n => ys += n}
    measureCompareTime("mutable Vector", xsFunc)("immutable ArrayBuffer", ysFunc)
  }

  def insertTop(): Unit = {
    var xs = List.empty[Int]
    val ys = ListBuffer.empty[Int]
    val xsFunc = () => (1 to 10000) foreach {n => n :: xs}
    val ysFunc = () => (1 to 10000) foreach {n => n +=: ys}
    measureCompareTime("mutable List", xsFunc)("immutable ListBuffer", ysFunc)
  }


}