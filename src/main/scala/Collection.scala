import services.BenchMarkUtils

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Collection {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  // compare seq sorted with sorting
  def sorting(): Unit = {
    import scala.util.{Random, Sorting}
    val NUM = 10000
    val target: Seq[Int] = (1 to NUM).map { _ => Random.nextInt(NUM) }
    measureCompareTime("scala.collection.sorted", () => target.sorted)("scala.util.Sorting", () => Sorting.stableSort(target))
  }

  // compare with seq map func with set map func
  def collectionMap():Unit = {
    val N: Int = 10000
    val seq = (1 to N).toSeq
    val set = (1 to N).toSet
    measureCompareTime("seq map %2", () => seq map { _ % 2 })("set map %2", () => set map { _ % 2 })
    measureCompareTime("seq map %1000", () => seq map { _ % 1000 })("set map %1000", () => set map { _ % 1000 })
  }

  // compare Nil join with collection apply
  def constructSeq(): Unit = {
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
    val xs = List.empty[Int]
    val ys = ListBuffer.empty[Int]
    val xsFunc = () => (1 to 10000) foreach {n => n :: xs}
    val ysFunc = () => (1 to 10000) foreach {n => n +=: ys}
    measureCompareTime("mutable List", xsFunc)("immutable ListBuffer", ysFunc)
  }

  def sizeOrLength(): Unit = {
    val array = (1 to 100000).toArray
    measureCompareTime("array.length", () => array.length)("array.size", () => array.size)
  }

  def emptyDeclaration(): Unit = {
    measureCompareTime("Seq[Int]()", () => Seq[Int]())("Seq.empty", () => Seq.empty)
  }

  def emptyCheck(): Unit = {
    val seq = 1 :: 2 :: 3 :: Nil
    measureCompareTime("!seq.isEmpty", () => !seq.isEmpty)("seq.nonEmpty", () => seq.nonEmpty)
  }

}