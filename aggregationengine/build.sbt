name := "aggregationengine"
version := "0.1"
scalaVersion := "2.11.8"
val sparkVersion = "2.0.0"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=  "org.apache.spark" %% "spark-core" % sparkVersion % Provided
libraryDependencies +=  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
libraryDependencies +=  "org.apache.spark" %% "spark-core" % sparkVersion % Provided
libraryDependencies +=  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
libraryDependencies +=  "org.apache.spark" %% "spark-catalyst" % sparkVersion % Provided
libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test->default"
libraryDependencies += "com.typesafe.scala-logging" % "scala-logging_2.11" % "3.1.0"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}