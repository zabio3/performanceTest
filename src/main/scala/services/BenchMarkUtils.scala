object BenchMarkUtils {

  def measure(n: String)(cmd: => Any) = {
    val startTime = System.nanoTime()
    cmd
    val endTime = System.nanoTime()
    val runTime = endTime - startTime
    println(s"$n の計測でかかった時間 : $runTime ナノ秒")
  }

  def measureRunTime(cmd: => Any):Long = {
    val startTime = System.nanoTime()
    cmd
    val endTime = System.nanoTime()
    endTime - startTime
  }

  def compareMeasure(n:String, x: => Any)(m:String, y: => Any) = {
    val masureTime = measureRunTime _
    val xTime = masureTime(x)
    val yTime = masureTime(y)
    println(s"$n の計測でかかった時間 : $xTime ナノ秒")
    println(s"$m の計測でかかった時間 : $yTime ナノ秒")
    println(s"差分 : ${xTime - yTime}")
  }



}
