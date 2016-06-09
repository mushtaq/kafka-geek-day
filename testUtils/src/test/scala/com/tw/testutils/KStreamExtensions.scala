package com.tw.testutils

import org.apache.kafka.streams.kstream.KStream

import scala.collection.mutable

object KStreamExtensions {

  implicit class RichKStream[K, V](stream: KStream[K, V]) {
    def buffer(): mutable.Buffer[(K, V)] = {
      val xs = mutable.Buffer.empty[(K, V)]
      stream.foreach((k, v) => xs += (k -> v))
      xs
    }
  }

}
