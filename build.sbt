import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "impatient_exercises"

lazy val root = (project in file("."))
  .settings(
    name := "scala-impatient",
    Test / fork := true,
    parallelExecution in Test := false,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % Test,
    libraryDependencies += "org.scalatestplus" %% "scalacheck-1-14" % "3.1.1.1" % Test,
    libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test,
    libraryDependencies += "org.jsoup" % "jsoup" % "1.12.1",
    libraryDependencies += {
      val version = scalaBinaryVersion.value match {
        case "2.10" => "1.0.3"
        case _ ⇒ "2.1.4"
      }
      "com.lihaoyi" % "ammonite" % version % "test" cross CrossVersion.full
    }
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
