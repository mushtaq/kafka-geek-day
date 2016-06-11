package com.tw.data

import com.tw.{ActualVisit, Value2, Value3, VisitAgg}

object Data {

  val usVisits = Seq(
    ActualVisit(region = "US", location = "pune", epoch = 1),
    ActualVisit(region = "US", location = "pune", epoch = 2),
    ActualVisit(region = "US", location = "pune", epoch = 3),
    ActualVisit(region = "US", location = "pune", epoch = 5),
    ActualVisit(region = "US", location = "pune", epoch = 6),
    ActualVisit(region = "US", location = "pune", epoch = 8),
    ActualVisit(region = "US", location = "pune", epoch = 9),
    ActualVisit(region = "US", location = "pune", epoch = 10),
    ActualVisit(region = "US", location = "pune", epoch = 12),
    ActualVisit(region = "US", location = "pune", epoch = 15)
  )

  val europeVisits = Seq(
    ActualVisit(region = "Europe", location = "pune", epoch = 1),
    ActualVisit(region = "Europe", location = "pune", epoch = 2),
    ActualVisit(region = "Europe", location = "pune", epoch = 3),
    ActualVisit(region = "Europe", location = "pune", epoch = 4),
    ActualVisit(region = "Europe", location = "pune", epoch = 5),
    ActualVisit(region = "Europe", location = "pune", epoch = 6),
    ActualVisit(region = "Europe", location = "pune", epoch = 7)
  )

  val aggs = Seq(
    VisitAgg(region = "US", location = "pune", 10),
    VisitAgg(region = "Europe", location = "pune", 7)
  )

  val aggs2 = Seq(
    VisitAgg(region = "US", location = "pune", 10),
    VisitAgg(region = "Europe", location = "pune", 7)
  )

  val ys = Seq.empty[Value2]
  val zs = Seq.empty[Value3]

}
