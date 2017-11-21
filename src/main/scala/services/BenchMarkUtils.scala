object BenchMarkUtils {

  /*
  val measure : Any => Any = cmd => {
    val startTime = System.nanoTime()
    cmd
    val endTime = System.nanoTime()
    val runTime = endTime - startTime
    println("計測でかかった時間 : " +  runTime + " ナノ秒")
  }
  */

  def measure(cmd: => Any) = {
    val startTime = System.nanoTime()
    cmd
    val endTime = System.nanoTime()
    val runTime = endTime - startTime
    println("計測でかかった時間 : " +  runTime + " ナノ秒")
  }

}
