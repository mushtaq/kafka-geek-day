package com.tw.geekday.cep

import com.tw.data.Data
import com.tw.geekday.TestAssembly
import com.tw.geekday.framework.ConnectionParams
import com.tw.geekday.utils.Topics
import com.tw.testutils.KStreamExtensions.RichKStream
import com.tw.testutils.KafkaSuite
import com.tw.{Key, Value1, Value2, Value3}
import io.confluent.examples.streams.IntegrationTestUtils
import io.confluent.examples.streams.kafka.EmbeddedSingleNodeKafkaCluster
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream

import scala.collection.JavaConverters._

class CepTest extends KafkaSuite {

  val privateCluster = new EmbeddedSingleNodeKafkaCluster()

  test("should join topic1 with topic2") {

    withRule(privateCluster) { cluster =>

      //create topics
      cluster.createTopic(Topics.Topic1)
      cluster.createTopic(Topics.Topic2)
      cluster.createTopic(Topics.Topic3)

      //create the assembly
      val clusterParams = ConnectionParams(
        cluster.bootstrapServers(),
        cluster.zookeeperConnect(),
        cluster.schemaRegistryUrl()
      )

      val assembly = new TestAssembly("cep-test", clusterParams)
      import assembly._

      //cleanup
      IntegrationTestUtils.purgeLocalStreamsState(streamsConfiguration)

      //be ready to read from output stream and buffer it for the assertion later
      val zStream: KStream[Key, Value3] = builder.stream(Topics.Topic3)

      val zs = zStream.buffer()

      //start cep and streams, give it some time to load before data setup
      cep.process(
        builder.stream(Topics.Topic1),
        builder.table(Topics.Topic2)
      ).to(Topics.Topic3)

      streams.start()
      Thread.sleep(1000)

      //setup data, give it some time to finish before stopping the app
      val xEvents: Seq[KeyValue[Key, Value1]] = Data.xs.map(e => new KeyValue(null.asInstanceOf[Key], e))

      IntegrationTestUtils.produceKeyValuesSynchronously(
        Topics.Topic1, xEvents.asJava, kafkaConfigurations.producerConfiguration()
      )

      val yEvents: Seq[KeyValue[Key, Value2]] = Data.ys.map(e => new KeyValue(null.asInstanceOf[Key], e))

      IntegrationTestUtils.produceKeyValuesSynchronously(
        Topics.Topic2, yEvents.asJava, kafkaConfigurations.producerConfiguration()
      )

      Thread.sleep(2000)

      //stop the streams
      streams.close()

      //assert
      zs.toMap.values.toSet should be(Data.zs.toSet)
    }
  }

}
