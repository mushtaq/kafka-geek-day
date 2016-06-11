package com.tw.geekday.cep

import com.tw.data.Data
import com.tw.geekday.TestAssembly
import com.tw.geekday.framework.ConnectionParams
import com.tw.geekday.utils.{KeyHelpers, Topics}
import com.tw.testutils.KStreamExtensions.RichKStream
import com.tw.testutils.KafkaSuite
import com.tw._
import io.confluent.examples.streams.IntegrationTestUtils
import io.confluent.examples.streams.kafka.EmbeddedSingleNodeKafkaCluster
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.{KStream, Windowed}

import scala.collection.JavaConverters._

class VisitsTest extends KafkaSuite {

  val privateCluster = new EmbeddedSingleNodeKafkaCluster()

  test("should aggregate visits") {

    withRule(privateCluster) { cluster =>

      //create topics
      cluster.createTopic(Topics.Visits)
      cluster.createTopic(Topics.Aggregates)

      //create the assembly
      val clusterParams = ConnectionParams(
        cluster.bootstrapServers(),
        cluster.zookeeperConnect(),
        cluster.schemaRegistryUrl()
      )

      val assembly = new TestAssembly("aggregation-test", clusterParams)
      import assembly._

      //cleanup
      IntegrationTestUtils.purgeLocalStreamsState(streamsConfiguration)

      //be ready to read from output stream and buffer it for the assertion later
      val aggregationStream: KStream[WindowedKey, VisitAgg] = builder.stream(Topics.Aggregates)

//      aggregationStream.foreach((k, v) => println(k, v))

      val results = aggregationStream.buffer()

      //start cep and streams, give it some time to load before data setup
      visitsCep.process2(
        builder.stream(Topics.Visits)
      ).to(Topics.Aggregates)

      streams.start()
      Thread.sleep(1000)

      //setup data, give it some time to finish before stopping the app
      val visitEvents: Seq[KeyValue[Key, ActualVisit]] = Data.usVisits.map { e =>
        Thread.sleep(10)
        new KeyValue(KeyHelpers.from(e), e)
      }

      IntegrationTestUtils.produceKeyValuesSynchronously(
        Topics.Visits, visitEvents.asJava, kafkaConfigurations.producerConfiguration()
      )

      Thread.sleep(2000)

      //stop the streams
      streams.close()

      //assert
      results.toMap.toList.sortBy(_._1.windowEndTime).foreach(println)
//      results.toMap.values.toSet should be(Data.aggs.toSet)
    }
  }

}
