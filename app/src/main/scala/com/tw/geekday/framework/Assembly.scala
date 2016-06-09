package com.tw.geekday.framework

import com.tw.geekday.cep.{Cep, Pipeline}
import com.typesafe.config.ConfigFactory
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.kstream.KStreamBuilder

abstract class AbstractAssembly(env: String, serviceName: String) {
  def connectionParams: ConnectionParams

  lazy val config = ConfigFactory.load(env)

  lazy val appSettings = new AppSettings(config)

  lazy val kafkaConfigurations = new KafkaConfigurations(connectionParams, appSettings)
  lazy val streamsConfiguration = kafkaConfigurations.streamsConfiguration(serviceName)

  lazy val builder = new KStreamBuilder()
  lazy val streams = new KafkaStreams(builder, streamsConfiguration)

  lazy val cep = new Cep

  lazy val pipeline = new Pipeline(cep, builder, streams)
}

class Assembly(env: String, serviceName: String) extends AbstractAssembly(env, serviceName) {
  lazy val connectionParams = ConnectionParams.from(config)
}
