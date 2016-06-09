import sbt._

object Dependencies {

  object Version {
    val Confluent = "3.0.0"
    val ScalaTest = "3.0.0-RC1"
    val Kafka = "0.10.0.0-cp1"
    val Play = "2.5.3"
    val Avro = "1.8.0"
    val ZkClient = "0.8"
    val Config = "1.3.0"
    val Avro4s = "1.4.3"
  }

  lazy val appDependencies = Seq(
    "org.apache.kafka" % "kafka-streams" % Version.Kafka,
    "com.typesafe" % "config" % Version.Config,
    "com.sksamuel.avro4s" %% "avro4s-core" % Version.Avro4s,

    "io.confluent" % "kafka-avro-serializer" % Version.Confluent,
    "io.confluent" % "kafka-schema-registry" % Version.Confluent,
    "io.confluent" % "kafka-schema-registry-client" % Version.Confluent,
    "org.apache.kafka" % "kafka-clients" % Version.Kafka,

    "org.apache.avro" % "avro" % Version.Avro,

    "com.101tec" % "zkclient" % Version.ZkClient excludeAll(
      ExclusionRule("javax.jms", "jms"),
      ExclusionRule("com.sun.jdmk", "jmxtools"),
      ExclusionRule("com.sun.jmx", "jmxri")
    )
  )

  lazy val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % Version.ScalaTest % "test",
    "junit" % "junit" % "4.12" % "test",
    "org.apache.kafka" %% "kafka" % Version.Kafka % "test" classifier "test",
    "org.apache.curator" % "curator-test" % "2.9.0" % "test",
    "io.confluent" % "kafka-schema-registry" % Version.Confluent % "test" classifier "tests",
    "org.scalactic" %% "scalactic" % Version.ScalaTest % "test"
  )
}
