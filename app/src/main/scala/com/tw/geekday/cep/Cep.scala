package com.tw.geekday.cep

import com.tw._
import org.apache.kafka.streams.kstream.{KStream, KTable}

class Cep {

  def process(
    stream1: KStream[Key, Value1],
    table2: KTable[Key, Value2]
  ): KStream[Key, Value3] = {

    ???
  }

}
