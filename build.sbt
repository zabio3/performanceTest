//noinspection SpellCheckingInspection
name := """performanceTest"""

version := "1.0-SNAPSHOT"
scalaVersion := "2.12.3"

//noinspection SpellCheckingInspection,SpellCheckingInspection
scalacOptions ++= Seq(
  "-deprecation", // 今後廃止の予定のAPIを利用している
  "-encoding", "utf8",
  "-feature", // 明示的に使用を宣言しないといけない実験的な機能や注意しなければならない機能を利用している
  "-unchecked", // 型消去などでパターンマッチが有効に機能しない場合
  "-Xlint" // その他、望ましい書き方や落とし穴についての情報
  // "-Xfatal-warnings" // 警告をエラーとして扱う
)
