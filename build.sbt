name := "Simple Project"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.4.0",
  "org.atilika.kuromoji" % "kuromoji" % "0.7.7"
)
