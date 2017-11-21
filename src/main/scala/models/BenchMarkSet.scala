package models
// cmd: () => Anyで書くと何かしら値をとる時にかっこが必要になる
case class BenchMarkSet[T <: Any](val name: String, cmd: () => T) {}

object BenchMarkSet {
  // override def apply[T <: Any](name: String, cmd: () => T): BenchMarkSet[T] = new BenchMarkSet[T](name, cmd)
}

