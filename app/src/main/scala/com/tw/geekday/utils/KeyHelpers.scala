package com.tw.geekday.utils

import com.tw.{ActualVisit, Key, WindowedKey}
import org.apache.kafka.streams.kstream.Windowed

object KeyHelpers {

  def from(visit: ActualVisit) = Key(
    region = visit.region,
    location = visit.location
  )

  def toWindowed(windowed: Windowed[Key]) = WindowedKey(
    region = windowed.key.region,
    location = windowed.key.location,
    windowEndTime = windowed.window().end()
  )

}
