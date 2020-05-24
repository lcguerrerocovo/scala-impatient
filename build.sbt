import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "impatient_exercises"

lazy val root = (project in file("."))
  .settings(
    name := "scala-impatient",
    libraryDependencies += scalaTest % Test ,
    libraryDependencies += {
      val version = scalaBinaryVersion.value match {
        case "2.10" => "1.0.3"
        case _ ⇒ "2.1.4"
      }
      "com.lihaoyi" % "ammonite" % version % "test" cross CrossVersion.full
    }
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
