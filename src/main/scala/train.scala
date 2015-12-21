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

object NaiveBayesSample {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("simple application").setMaster("local")
    val sc = new SparkContext(conf)
    val data = sc.textFile("data/jawiki-latest-pages-articles.tsv").cache()
    val labels = data.map{_.split("\t")(0)}
    val texts =  data.map{_.split("\t")(1)}.map{get_words(_)}.map{_.toSeq}
    val htf = new HashingTF(1000)
    val tf = htf.transform(texts)
    val idf = new IDF().fit(tf)
    val tfidf = idf.transform(tf)
    val training = labels.zipWithIndex().map(_._2.toDouble).zip(tfidf).map(x => LabeledPoint(x._1, x._2))
    val model = NaiveBayes.train(training)
    model.save(sc, "model")
    tfidf.saveAsTextFile("model/tfidf.txt")
    sc.stop()
  }

  def debug_message() = { println("#" * 100) }
  def get_words(words: String) = {
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

