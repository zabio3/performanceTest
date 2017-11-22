import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  Collection.sorting()
  Collection.collectionMap()
  Collection.constractSeq()
  Collection.mapToCollection()

  Util.random()

  Synchronization.futureTrap()

  Other.callByName()
  Other.valueClass()
  Other.reflectionStructureSubType()

}