import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token

object SparkMllibSample {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("simple application").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val data = sc.textFile("data/sample.txt")
    val train_data = data.map{d =>
      val label = d.split(",")(0)
      val word  = d.split(",")(1)
      (label, get_words_two(word))
    }
    val w2v = train_data.map(_._2.toSeq)
    // w2v.foreach(println)
    val model = new Word2Vec().fit(w2v)
    val synonyms = model.findSynonyms("簡単", 1)
    for((synonym, consineSimilarity) <- synonyms) {
      println(s"$synonym $consineSimilarity")
    }
    model.save(sc, "data/mymodel")
    val sameModel = Word2VecModel.load(sc, "data/mymodel")
    // sc.stop // sc destroy
  }

  def get_words(words: String) = {
    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build
    tokenizer.tokenize(words).toArray.filter{t =>
      val token = t.asInstanceOf[Token].getAllFeatures.split(",")(0)
      token == "名詞" || token == "形容詞"
    }.map{t =>
      t.asInstanceOf[Token].getSurfaceForm
    }.toList.groupBy(identity).mapValues(_.size)
  }

  def get_words_two(words: String) = {
    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build
    tokenizer.tokenize(words).toArray.filter{t =>
      val token = t.asInstanceOf[Token].getAllFeatures.split(",")(0)
      token == "名詞" || token == "形容詞"
    }.map{t =>
      t.asInstanceOf[Token].getSurfaceForm
    }
  }

}
