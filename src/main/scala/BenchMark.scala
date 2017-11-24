import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  Collection.compareFibonacci()

  /*
  Collection.sizeOrLength()
  Collection.sorting()
  Collection.collectionMap()
  Collection.constructSeq()
  Collection.mapToCollection()
  Collection.loop()
  Collection.iteratorCompareStream()
  Collection.streamCompare()
  Collection.addingTail()
  Collection.insertTop()
  Collection.emptyDeclaration()
  Collection.emptyCheck()
  Collection.compareLength()
  Collection.compareExists()
  Collection.compareEquals()
  Collection.compareDelete()
  Collection.randomRead()

  Util.random()

  LanguageFeature.futureTrap()
  LanguageFeature.callByName()
  LanguageFeature.valueClass()
  LanguageFeature.reflectionStructureSubType()
  */

}