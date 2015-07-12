import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
// import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
// import org.apache.spark.mllib.linalg.Vectors
// import org.apache.spark.mllib.regression.LabeledPoint
import org.atilika.kuromoji.Tokenizer
import org.atilika.kuromoji.Token
import scala.io.Source

object Hello {
  def main(args: Array[String]) = {
    val conf = new SparkConf().setAppName("simple application").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val data = Source.fromFile("data/sample.txt").getLines.toArray.mkString(" ")
    val tokenizer = Tokenizer.builder.mode(Tokenizer.Mode.NORMAL).build
    val tokens = tokenizer.tokenize(data).toArray
    tokens.foreach { t =>
      val token = t.asInstanceOf[Token]
      println(s"${token.getSurfaceForm} - ${token.getAllFeatures}")
    }
    sc.stop
  }
}
