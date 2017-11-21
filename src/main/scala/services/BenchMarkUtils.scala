package services

object BenchMarkUtils extends UnitCalcable {

  def measure(n: String)(cmd: () => Any) = {
    val runTime = this.measureRunTime(cmd)
    println(s"$n の計測でかかった時間 : $runTime ナノ秒")
  }

  private def measureRunTime(cmd: () => Any): Double = {
    val startTime = System.nanoTime()
    cmd()
    val endTime = System.nanoTime()
    (endTime - startTime) / Giga
  }

  def compareMeasure(n:String, x:() => Any)(m:String, y:() => Any) = {
    val masureTime = measureRunTime _
    val xTime = masureTime(x)
    val yTime = masureTime(y)
    val xUnitTime = convertUnit(xTime)
    val yUnitTime = convertUnit(yTime)
    val diffUnitTime = convertUnit(xTime - yTime)

    println(s"$n の計測でかかった時間 : ${xUnitTime._1}${xUnitTime._2}秒")
    println(s"$m の計測でかかった時間 : ${yUnitTime._1}${yUnitTime._2}秒")
    println(s"$n - $m の差分 : ${diffUnitTime._1}${diffUnitTime._2}秒")
  }

  /*
  import models.BenchMarkSet
  def compareMeasureA[T1,T2 <: Any](cmdSetX: BenchMarkSet[T1])(cmdSetY: BenchMarkSet[T2]) = {
    val measureTime = measureRunTime _
    val xTime = measureTime(cmdSetX.cmd)
    val yTime = measureTime(cmdSetY.cmd)
    println(s"${cmdSetX.name} の計測でかかった時間 : $xTime ナノ秒")
    println(s"${cmdSetY.name} の計測でかかった時間 : $yTime ナノ秒")
    println(s"差分 : ${xTime - yTime}")
  }
  */

}
