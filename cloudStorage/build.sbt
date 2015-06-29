import sbt._

name := "Testing and Tailoring cloud storages"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

fork in run := true

libraryDependencies += jdbc

libraryDependencies += anorm

libraryDependencies +=  "mysql" % "mysql-connector-java" % "5.1.35"

libraryDependencies +=   "com.typesafe.akka" %% "akka-testkit" % "2.3.3"
