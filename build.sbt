enablePlugins(JavaAppPackaging)

name := "bh.rest.cerner"
organization := "bh"
version := "0.1"
scalaVersion := "2.12.10"
libraryDependencies ++= {
  val akkaVersion = "2.5.25"
  val akkkHttpVersion = "10.1.10"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.29.1",
    "org.scalikejdbc" %% "scalikejdbc" % "3.4.0",
    "com.oracle.ojdbc" % "ojdbc8" % "19.3.0.0",
    "com.lihaoyi" %% "upickle" % "0.8.0",
    "com.typesafe" % "config" % "1.3.4",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.8" % Test
  )
}