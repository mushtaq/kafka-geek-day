package com.tw.geekday.cep

import com.tw.geekday.utils.Topics
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.kstream.KStreamBuilder

class Pipeline(
  cep: Cep,
  builder: KStreamBuilder,
  streams: => KafkaStreams) {

  def run() = {

    cep.process(
      builder.stream(Topics.Visits),
      builder.table(Topics.Topic2)
    ).to(Topics.Aggregates)

    streams.start()
  }

  def stop() = {
    streams.close()
  }
}
