package com.tw.geekday.framework

import java.util.Properties

import io.confluent.examples.streams.utils.SpecificAvroSerde
import io.confluent.kafka.serializers.{AbstractKafkaAvroSerDeConfig, KafkaAvroDeserializer, KafkaAvroSerializer}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.streams.StreamsConfig

class KafkaConfigurations(clusterParams: ConnectionParams, appSettings: AppSettings) {

  def streamsConfiguration(applicationId: String): Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId)
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, clusterParams.kafkaServers)
    p.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, clusterParams.zookeeperServers)
    p.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, classOf[SpecificAvroSerde[_]])
    p.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, classOf[SpecificAvroSerde[_]])
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, clusterParams.schemaRegistryUrl)
    p.put(StreamsConfig.STATE_DIR_CONFIG, appSettings.KafkaStreamStateDir)
    p
  }

  def producerConfiguration(): Properties = {
    val p = new Properties()
    p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, clusterParams.kafkaServers)
    p.put(ProducerConfig.ACKS_CONFIG, "all")
    p.put(ProducerConfig.RETRIES_CONFIG, "0")
    p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
    p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, clusterParams.schemaRegistryUrl)
    p
  }

  def consumerConfiguration(groupId: String) = {
    val p = new Properties()
    p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, clusterParams.kafkaServers)
    p.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[KafkaAvroDeserializer])
    p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[KafkaAvroDeserializer])
    p.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, clusterParams.schemaRegistryUrl)
    p
  }

}
