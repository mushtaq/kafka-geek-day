package com.tw.geekday.utils

import com.tw.ActualVisit
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.streams.processor.TimestampExtractor

class CustomTimeExtractor extends TimestampExtractor {
  def extract(record: ConsumerRecord[AnyRef, AnyRef]): Long = record.value() match {
    case x: ActualVisit => x.epoch
    case x              => record.timestamp()
  }
}
