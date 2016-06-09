import sbt.Keys._
import sbt._

object Settings {
  private lazy val compilerOptions = Seq(
    "-Xexperimental",
    "-deprecation",
    "-unchecked",
    "-feature",
    "-encoding", "UTF-8"       // yes, this is 2 args
  )

  lazy val commonSettings = Seq(
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalaVersion := "2.11.8",
    scalacOptions ++= compilerOptions,
    resolvers += "confluent" at "http://packages.confluent.io/maven/",
    parallelExecution in Test := false
  )

  lazy val suppressDocPublishing = Seq(
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in (Compile, packageSrc) := false,
    publishArtifact in packageDoc := false,
    publishArtifact in packageSrc := false,
    sources in (Compile, doc) := Seq.empty
  )
}
