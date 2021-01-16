enablePlugins(JavaAppPackaging)

name := "akka.http.medical"
organization := "objektwerks"
version := "0.1"
scalaVersion := "2.13.4"
libraryDependencies ++= {
  val akkaVersion = "2.6.11"
  val akkkHttpVersion = "10.2.2"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.35.0",
    "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
    "com.oracle.ojdbc" % "ojdbc8" % "19.3.0.0",
    "com.lihaoyi" %% "upickle" % "1.2.2",
    "com.typesafe" % "config" % "1.4.0",
    "com.iheart" %% "ficus" % "1.5.0",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.3" % Test
  )
}
