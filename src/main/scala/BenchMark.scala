import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  Collection.sorting()
  Collection.collectionMap()
  Collection.constractSeq()
  Collection.mapToCollection()
  Collection.loop()
  Collection.iteratorCompareStream()
  Collection.streamCompare()
  Collection.addingTail()
  Collection.insertTop()

  Util.random()

  Synchronization.futureTrap()

  Other.callByName()
  Other.valueClass()
  Other.reflectionStructureSubType()

}