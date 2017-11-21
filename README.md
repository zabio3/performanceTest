# performanceTest
Performance test with scala code

measure runtime 

| Compare | TargetA | TargetB | result |
| -------- | -------- | -------- | -------- |
| Sorting | scala.collection.sort | scala.util.sorting | < |
| Random | scala.util.Random | scala.concurrent.forkjoin.ThreadLocalRandom | > |


## Reference

 - [Scala perfomance tips - ScalaMatsuri2017](https://speakerdeck.com/petitviolet/scala-performance-tips-scalamatsuri2017)

