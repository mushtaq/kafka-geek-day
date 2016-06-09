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
      builder.stream(Topics.Topic1),
      builder.table(Topics.Topic2)
    ).to(Topics.Topic3)

    streams.start()
  }

  def stop() = {
    streams.close()
  }
}
