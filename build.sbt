
lazy val app = project
  .dependsOn(utils)
  .enablePlugins(JavaAppPackaging)
  .settings(Settings.commonSettings)
  .settings(Settings.suppressDocPublishing)
  .settings(sbtavrohugger.SbtAvrohugger.specificAvroSettings)
  .settings(
    name := "tw-kafka-geekday",
    organization := "com.tw",
    version := "1.0",
    libraryDependencies ++= Dependencies.appDependencies,
    libraryDependencies ++= Dependencies.testDependencies,
    dockerBaseImage := "java:openjdk-8-jre"
  )


lazy val utils = project
  .settings(Settings.commonSettings)
  .settings(Settings.suppressDocPublishing)
  .settings(
    libraryDependencies ++= Dependencies.appDependencies
  )


lazy val integration = project
  .dependsOn(app, utils, testUtils % "test->test")
  .settings(Settings.commonSettings)
  .settings(
    libraryDependencies ++= Dependencies.testDependencies
  )


lazy val testUtils = project
  .settings(Settings.commonSettings)
  .settings(
    libraryDependencies ++= Dependencies.appDependencies,
    libraryDependencies ++= Dependencies.testDependencies
  )
