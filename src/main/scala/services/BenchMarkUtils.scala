package services

object BenchMarkUtils extends UnitCalcable {

  def measure(n: String)(cmd: () => Any) = {
    val runTime = this.measureRunTime(cmd)
    println(s"$n was measure time : $runTime nano seconds")
  }

  def compareMeasure(n:String, x:() => Any)(m:String, y:() => Any) = {
    val masureTime = measureRunTime _
    val xTime = masureTime(x)
    val yTime = masureTime(y)
    val xUnitTime = convertUnit(xTime)
    val yUnitTime = convertUnit(yTime)
    val diffUnitTime = convertUnit(xTime - yTime)
    val divisionVolume = xTime / yTime

    println("========================================================================================")
    println(s"$n was measure time : ${xUnitTime._1} ${xUnitTime._2} Seconds")
    println(s"$m was measure time : ${yUnitTime._1} ${yUnitTime._2} Seconds")
    println(s"$n - $m (difference): ${diffUnitTime._1} ${diffUnitTime._2} Seconds")
    println(s"$n / $m (division) : ${divisionVolume} times")
    println("========================================================================================")
  }

  private def measureRunTime(cmd: () => Any): Double = {
    val startTime = System.nanoTime()
    cmd()
    val endTime = System.nanoTime()
    (endTime - startTime) / Giga
  }

}
