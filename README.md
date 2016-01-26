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

## 心配事
- 分散されていない処理がある
  + tf-idf
  + naive bayes train

- yarn上で実行できるのか？
  + sbt assemblyでjarに固めればokなはず...
  + spark-submitで分散処理が動作することを期待

