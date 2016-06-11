package com.tw.geekday.cep

import com.tw._
import org.apache.kafka.streams.kstream.{JoinWindows, KStream, KTable}

class Cep {

  def process(
    stream1: KStream[Key, ActualVisit],
    table2: KTable[Key, Value2]
  ): KStream[Key, Value3] = {

//    stream1.reduceByKey()

    ???
  }

}
