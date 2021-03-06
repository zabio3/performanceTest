# performanceTest

[![CircleCI](https://circleci.com/gh/zabio3/performanceTest.svg?style=svg)](https://circleci.com/gh/zabio3/performanceTest)

[Japanese](text_ja/README.md)

performance regression testing ( sorting, instance create, evaluation etc ...)

### Usage

```
sbt run
```

### Results (measure runtime)
| Compare | TargetA | TargetB | TargetC | Result |
| -------- | -------- | -------- | -------- | -------- |
| Sorting | scala.collection.sort | scala.util.sorting | - | scala.util.sorting is much faster |
| to collection after map| toXXX (XXX is collection)| use breakout | - | using breakout is faster |
| loop | for | foldLeft | - | 'for'  is faster |
| collection map func | seq map func | set map func  | - | seq is much faster |
| constructSeq | Seq apply | List apply | ::Nil | ::Nil is great |
| lazy evaluate deta structure | stream | list  | - | stream is more faster |
| adding at the tail | mutable 'Vector' | immutable 'ArrayBuffer'   | - | 'ArrayBuffer' is more faster |
| insert top | mutable 'List' | immutable 'ListBuffer'   | - | 'List' is faster 'ListBuffer' |
| array element size |length|size|-|Prefer length to size for arrays|
| Random | scala.util.Random | java.util.concurrent.ThreadLocalRandom (alias of scala.concurrent.forkjoin.ThreadLocalRandom) | - | ThreadLocalRandom is faster than Random |
| future position | inside | outside  | - | Be careful about timing of 'Future.apply' |
| argument evaluation | call-by-name | ordinary value | - | when argument evaluate that not use call-by-name, call-by-name was much faster |
| valueClass instance | normal | extends AnyVal  | - | 'Extends AnyVal' is much faster |
| structure sub type (reflection)| not use | use structure sub type | - | not use structure sub type is much faster |

## Reference
 - [Scala Collections Tips and Tricks](https://pavelfatin.com/scala-collections-tips-and-tricks/)
 - [Scala performance tips - ScalaMatsuri2017](https://speakerdeck.com/petitviolet/scala-performance-tips-scalamatsuri2017)
 - [structure sub type](http://tech-blog.tsukaby.com/archives/849)
 - [scala best practice](http://xuwei-k.hatenablog.com/entry/20130709/1373330529)
 - [collection performance](http://docs.scala-lang.org/ja/overviews/collections/performance-characteristics.html)
 - [performance ScalaMatsuri2016](https://www.slideshare.net/x1ichi/scala-57670004)