package com.tw.geekday.cep

import com.tw._
import com.tw.geekday.utils.KeyHelpers
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream._

class VisitsCep {

  def process(visits: KStream[Key, ActualVisit]): KTable[Key, VisitAgg] = {

    def aggr(k: Key, v: ActualVisit, acc: VisitAgg) = VisitAgg(
      v.region, v.location, acc.count + 1
    )

    visits.aggregateByKey(() => VisitAgg("", "", 0), aggr, "agg")

  }

  def process2(visits: KStream[Key, ActualVisit]): KStream[WindowedKey, VisitAgg] = {

    def aggr(k: Key, v: ActualVisit, acc: VisitAgg) = VisitAgg(
      v.region, v.location, acc.count + 1
    )

    val kTable: KTable[Windowed[Key], VisitAgg] =
      visits
        .aggregateByKey(() => VisitAgg("", "", 0), aggr, TimeWindows.of("wind", 5).advanceBy(1))

    kTable.toStream().map((k: Windowed[Key], v) => new KeyValue(KeyHelpers.toWindowed(k), v))
  }

}
