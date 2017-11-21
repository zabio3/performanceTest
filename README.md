# performanceTest
Performance test with scala code

measure runtime 

| Compare | TargetA | TargetB | TargetC | result |
| -------- | -------- | -------- | -------- | -------- |
| Sorting | scala.collection.sort | scala.util.sorting | - |  |
| Random | scala.util.Random | java.util.concurrent.ThreadLocalRandom (alias of scala.concurrent.forkjoin.ThreadLocalRandom) | - | |
| collection map func | seq map func | set map func  | - | seq |
| constractSeq | Seq apply | List apply | ::Nil | ::Nil is great |

## Reference

 - [Scala perfomance tips - ScalaMatsuri2017](https://speakerdeck.com/petitviolet/scala-performance-tips-scalamatsuri2017)

