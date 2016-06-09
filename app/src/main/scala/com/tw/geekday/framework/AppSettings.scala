package com.tw.geekday.framework

import com.typesafe.config.Config

class AppSettings(config: Config) {
  val KafkaStreamStateDir = config.getString("kafka-stream-state-dir")
}
