package services

object BenchMarkUtils {

  private val NANOSECONDS = 1000000000.0

  def measure(n: String)(cmd: () => Any) = {
    val runTime = this.measureRunTime(cmd)
    println(s"$n の計測でかかった時間 : $runTime 秒")
  }

  private def measureRunTime(cmd: () => Any):Double = {
    val startTime = System.nanoTime()
    cmd()
    val endTime = System.nanoTime()
    (endTime - startTime) / NANOSECONDS
  }

  def compareMeasure(n:String, x:() => Any)(m:String, y:() => Any) = {
    val masureTime = measureRunTime _
    val xTime = masureTime(x)
    val yTime = masureTime(y)
    println(s"$n の計測でかかった時間 : ${xTime} 秒")
    println(s"$m の計測でかかった時間 : ${yTime} 秒")
    println(s"$n - $m の差分 : ${xTime - yTime} 秒")
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
