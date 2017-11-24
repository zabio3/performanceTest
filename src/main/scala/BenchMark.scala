import services.BenchMarkUtils

object BenchMark extends App {

  val measureCompareTime = BenchMarkUtils.compareMeasure _

  Collection.compareEquals()

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

  Util.random()

  LanguageFeature.futureTrap()
  LanguageFeature.callByName()
  LanguageFeature.valueClass()
  LanguageFeature.reflectionStructureSubType()
  */

}