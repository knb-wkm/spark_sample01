import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token
import scala.runtime.ScalaRunTime._

object NaiveBayesTrain {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("simple application").setMaster("local")
    val sc = new SparkContext(conf)
    val data = sc.textFile("data/jawiki-latest-pages-articles.tsv").cache()
    val labels = data.map{_.split("\t")(0)}
    val texts =  data.map{splitter(_)}.map{wakati(_)}.map{_.toSeq}
    val htf = new HashingTF(1000)
    val tf = htf.transform(texts)
    val idf = new IDF().fit(tf)
    val tfidf = idf.transform(tf)
    val training = labels.zipWithIndex().map(_._2.toDouble).zip(tfidf).map(x => LabeledPoint(x._1, x._2))
    val model = NaiveBayes.train(training)
    model.save(sc, "model")
    labels.saveAsObjectFile("model/labels")
    texts.saveAsObjectFile("model/texts")
    // val words = List("武将"," 大名", "天下人", "関白", "太閤")
    // val test_tf = htf.transform(words)
    // val test = model.predict(test_tf)
    // debug_message()
    // println(htf.getClass)
    // println(test)
    sc.stop()
  }

  def debug_message() = { println("#" * 100) }
  def splitter(word: String) = { if(word.split("\t").size == 1) "" else word.split("\t")(1) }
  def wakati(words: String) = {
    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build
    tokenizer.tokenize(words).toArray.filter{t =>
      val token = t.asInstanceOf[Token].getAllFeatures.split(",")(0)
      token == "名詞" || token == "形容詞"
    }.map{t =>
      t.asInstanceOf[Token].getSurfaceForm
    }
  }
}

// println(stringOf( Array(1,2,3) ))  // => Array(1, 2, 3)

