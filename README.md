# performanceTest
Performance test with scala code

### Results (measure runtime)
| Compare | TargetA | TargetB | TargetC | result |
| -------- | -------- | -------- | -------- | -------- |
| Sorting | scala.collection.sort | scala.util.sorting | - | scala.util.sorting is much faster |
| Random | scala.util.Random | java.util.concurrent.ThreadLocalRandom (alias of scala.concurrent.forkjoin.ThreadLocalRandom) | - | |
| collection map func | seq map func | set map func  | - | seq is much faster |
| constractSeq | Seq apply | List apply | ::Nil | ::Nil is great |
| argument evaluation | call-by-name | ordinary value | - | when argument evaluate that not use call-by-name, call-by-name was much faster |
| valueClass instance | normal | extends AnyVal  | - | 'Extends AnyVal' is much faster |

## Reference

 - [Scala perfomance tips - ScalaMatsuri2017](https://speakerdeck.com/petitviolet/scala-performance-tips-scalamatsuri2017)

