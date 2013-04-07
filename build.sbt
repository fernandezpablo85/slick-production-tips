name := "slick-connection-pool-example"

organization := "org.example"

version := "1.0"

scalaVersion := "2.10.0"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "1.0.0",
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "ch.qos.logback" % "logback-classic" % "1.0.1",
  "ch.qos.logback" % "logback-core" % "1.0.1",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "c3p0" % "c3p0" % "0.9.1.2"
)
