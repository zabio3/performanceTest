package models

case class BenchMarkSet[T <: Any](val name: String, cmd: () => T) {}

object BenchMarkSet {
  // override def apply[T <: Any](name: String, cmd: () => T): BenchMarkSet[T] = new BenchMarkSet[T](name, cmd)
}

object Normal {
  case class User(id: Id, name: Name)
  case class Id(value: Long)
  case class Name(value: String)
}

object Value {
  case class UserValue(id: IdValue, name: NameValue)
  case class IdValue(value: Long) extends AnyVal
  case class NameValue(value: String) extends AnyVal
}