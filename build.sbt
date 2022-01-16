enablePlugins(JavaAppPackaging)

name := "akka.http.medical"
organization := "objektwerks"
version := "0.1"
scalaVersion := "2.13.8"
libraryDependencies ++= {
  val akkaVersion = "2.6.18"
  val akkkHttpVersion = "10.2.7"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkkHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "de.heikoseeberger" %% "akka-http-upickle" % "1.39.2",
    "com.typesafe" %% "ssl-config-core" % "0.6.0",
    "org.scalikejdbc" %% "scalikejdbc" % "4.0.0",
    "com.oracle.ojdbc" % "ojdbc8" % "19.3.0.0",
    "com.lihaoyi" %% "upickle" % "1.4.4",
    "com.typesafe" % "config" % "1.4.1",
    "com.iheart" %% "ficus" % "1.5.1",
    "ch.qos.logback" % "logback-classic" % "1.2.10",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http-testkit" % akkkHttpVersion % Test,
    "org.scalatest" %% "scalatest" % "3.2.10" % Test
  )
}
