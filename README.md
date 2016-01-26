# naive bayes train
## 各種パラメータ
reference.confを参照

## jar化
```
$ sbt assembly
```

## 実行方法
yarn上で動作させたい人
```
$ spark-submit --master yarn target/scala-2.10/Train-assembly-1.0.jar
```

スタンドアロンで動作させたい人(テスト、開発)
```
$ sbt run
```

## コード解説
- build.sbt
  + mavenみたいなもの
  + xml, yamlなどではなくscalaで記載する

- resources.conf
  + train.scalaのパラメータを外部に逃したもの
  + typesafe.configにて作成(xxxx.propertyは?)
  + typesafe社が開発したライブラリ(java, scalaどっちでも)

- train.scala
  + あまり関数型っぽく実装できていない...
  + typesafe.configにてパラメータを呼び出し
  + あとはsparkドキュメントどおり
  + 日本語の処理なのでwakatiエンジンはkuromojiを使用した

## 心配事
- 分散されていない処理がある
  + tf-idf
  + naive bayes train

- yarn上で実行できるのか？
  + sbt assemblyでjarに固めればokなはず...
  + spark-submitで分散処理が動作することを期待

