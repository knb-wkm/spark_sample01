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

ローカル上で動作させたい人
```
$ sbt run
```