import sbt._

name := "Testing and Tailoring cloud storages"

version := "1.0-SNAPSHOT"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

fork in run := true

libraryDependencies += jdbc

libraryDependencies += anorm

libraryDependencies +=  "mysql" % "mysql-connector-java" % "5.1.35"


