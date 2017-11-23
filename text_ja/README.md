# performanceTest

[![CircleCI](https://circleci.com/gh/zabio3/performanceTest.svg?style=svg)](https://circleci.com/gh/zabio3/performanceTest)

Scalaでの実行速度のテスト（sbt-jmhを利用してもよかった）

### 実行方法
「BenchMark」に計測を行いたい対象のメソッドを追加し、下記コマンドを実行。
（内部のコレクションサイズなどのパラメータは自身で試してください）

```
sbt run
```

## 計測結果　

### ■ コレクション関連

#### ▼ ソート

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- |
| scala.collection.sort | scala.util.sorting | scala.util.sorting の方が早い |

ただし、Sortingはプリミティブ型に対してのみ有効で、独自型に対して用いても特に高速ではない。1Kで、約1.3倍。10Kで、約1.9倍。 

#### ▼ map処理後でのコレクションへの変換
| 対象A | 対象B | 総評 |
| -------- | -------- | -------- | 
| toXXX (XXX は、コレクション名)| breakOutの利用 | breakOutを利用した方が早い。|

mapでなくてもCanBuildFromを使っている関数であれば、breakOutを利用した方が早い。
mapは2種類あり、Seq(1,2).map(_ + 1)などでは、下記の方のmapが呼ばれている。

```
def map[B](f: Int => B): scala.collection.TraversableOnce[B]
```

implicit取る方を強制的に呼ぶためにbreakout渡してる + 好きな型にmapしつつ変換することで、コレクションを2回生成させる手間を1回に省くことが可能

```
def map[B, That](f: Int => B)(implicit bf: scala.collection.generic.CanBuildFrom[Seq[Int],B,That]): That   
```

#### ▼ Seq と Set のmap処理
mapの処理では、「%2」、「%1000」を行う

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- | 
| seq map| set map |値が大きい場合や、map後の処理が思いほど、seq の方が早い場合が多い |

10Kの「%2」は、seq より set の方が早かった。（約1.4倍）
10Kの「%1000」は、 seq より set の方が遅かった。（約2倍）
一般的には、seqの方がよいと言われている。

#### ▼ Seq の生成

| 対象A | 対象B | 対象C | 総評 |
| -------- | -------- | -------- | -------- | 
| Seq apply | List apply | ::Nil | ::Nil での生成がものすごく早い |

「:Nil」は、ただNewしているだけなので、ものすごく早い。

`1 :: Nil == new ::(1, Nil)`

対して、'Seq()', 'List()'は、'ListBuffer'を利用している。

```
val b = newBuilder[A] // mutable.ListBuffer
b ++ elems
b.result()
```

#### ▼ コレクションの一部利用
stream : 要素を遅延評価され、呼び出された要素のみが計算される。他の点は、Listと同じ
(要素の生成を後回しにして、無限の要素があるかのように振るまわせるイメージ)

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- | 
| stream | list | 一部のみ利用する場合は、streamの方が早い |

#### ▼ for と foldLeft の比較

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- | 
| for | foldLeft | 'for'の方がはやい |

 - [Scala foldLeft performance 4x worse than for loop when working with floats](https://stackoverflow.com/questions/24293545/scala-foldleft-performance-4x-worse-than-for-loop-when-working-with-floats)

#### ▼ 末尾追加

| 対象A | 対象B| 総評 |
| -------- | -------- | -------- | 
| 可変なVector | 不変なArrayBuffer | 不変なArrayBufferの方が早い |

 - 'var Vector'は、新しいインスタンスに既存の要素をコピーしてから、新しい要素を追加する。
 - 'val ArrayBuffer'は、インスタンスのリサイズを行ってから末尾の要素を新要素で更新する。


#### ▼ 先頭挿入

| 対象A | 対象B| 総評 |
| -------- | -------- | -------- | 
| 可変なList | 不変なListBuffer | 可変なListの方が早い |

- 'var List'は、先頭とその他の要素を別で管理しているため、新しい要素をすぐ作ることが可能。
- 'val ListBuffer'は、vaList内部変数内部変数の再代入を行う


## ■ 便利機能

#### ▼ Random値の生成

| 比較項目 | 対象A | 対象B| 総評 |
| -------- | -------- | -------- | -------- |
| Random | scala.util.Random | concurrent.forkjoin.ThreadLocalRandom | ThreadLocalRandomの方が早い。|

scala.concurrent.forkjoin.ThreadLocalRandomのエイリアスとして、java.util.concurrent.ThreadLocalRandomが存在する。
1Kで、約2倍、10Kで、約15倍程度。測定が安定しない場合もありその際は、約1.5倍て程度

## ■ 言語機能

#### ▼ 条件によって、引数を扱うメソッド

**名前渡し(call by name)**
 - 名前渡しされた引数は、使用される直前で評価される。ただし、[Scalaの名前渡しは遅延評価ではない](http://kannokanno.hatenablog.com/entry/20130202/1359777436) 。
>  遅延評価の定義は、一旦計算された値はキャッシュをすることが可能であり、遅延プロミスは最大で一度しか計算されないようにすることができる 

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- |
| 名前渡し | 値渡し | 名前渡しの方が、引数を使わない場合は処理が早い |

一般的に、call by nameは、loggerなどに使うのが便利。

**<名前渡しについて>**
 - 名前渡しは遅延評価を目的として使うのではなくて、「() =>」の冗長な記述をなくすために用いる
 - 値は再計算されるので、何度も実行したくない場合は一時変数に置くなどする
 - 名前渡しは無名クラス経由で実行されるので値渡しよりは効率が悪い
 - 名前渡しは無名クラスを作るので値渡しよりもスタックヒープを食う
 - Scalaの名前渡しは、"call by need"ではなく、"cal by name"である

#### ▼ 値クラスのインスタンス化

値クラスは、AnyValを継承し、ただ一つのValを持つもの

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- |
| 値クラスのインスタンス化 | (AnyValを継承しない)通常クラス  | 値クラスを継承した方が早い |

値クラスは、 DDD や 'implicit class' などで使いどころが多い。

#### ▼ Future と for式

Futureが複数あり、for式で展開していく場合

| 対象A | 対象B | 総評 |
| -------- | -------- | -------- | 
| for式の中に、Futureを定義し、for式で使用 |  for式の外に、Futureを定義し、for式で使用  | for式の外にFutureを出している方が早い |

for式は上から順に処理されていくため、Futureを複数for式で利用している場合は見直しが必要。（同期処理になるため）

## 参考
 - [Scala performance tips - ScalaMatsuri2017](https://speakerdeck.com/petitviolet/scala-performance-tips-scalamatsuri2017)
 - [Scalaの名前渡しは遅延評価ではない](http://kannokanno.hatenablog.com/entry/20130202/1359777436)
 - [コレクションの性能特性](http://docs.scala-lang.org/ja/overviews/collections/performance-characteristics.html)
 - [あなたのScalaを爆速にする７つの方法 - ScalaMatsuri2016](https://www.slideshare.net/x1ichi/scala-57670004)
 - [Scalaにおける細かい最適化のプラクティス](http://xuwei-k.hatenablog.com/entry/20130709/1373330529)


# その他
| 比較項目 | 対象A | 対象B | 対象C | 総評 |
| -------- | -------- | -------- | -------- | -------- |
| structure sub type (reflection)| not use | use structure sub type | - | not use structure sub type is much faster |


 - [Scala Collections Tips and Tricks](https://pavelfatin.com/scala-collections-tips-and-tricks/)

 - [structure sub type](http://tech-blog.tsukaby.com/archives/849)
