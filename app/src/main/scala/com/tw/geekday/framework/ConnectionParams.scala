package com.tw.geekday.framework

import com.typesafe.config.Config

case class ConnectionParams(
  kafkaServers: String,
  zookeeperServers: String,
  schemaRegistryUrl: String
)

object ConnectionParams {
  def from(config: Config) = ConnectionParams(
    kafkaServers = config.getString("kafka-servers"),
    zookeeperServers = config.getString("zookeeper-servers"),
    schemaRegistryUrl = config.getString("schema-registry-url")
  )
}
