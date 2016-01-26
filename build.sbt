name := "Train"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika"
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.5.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "1.5.0" % "provided",
  "org.atilika.kuromoji" % "kuromoji" % "0.7.7",
  "com.typesafe" % "config" % "1.2.1"
)

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))
runMain in Compile <<= Defaults.runMainTask(fullClasspath in Compile, runner in (Compile, run))
