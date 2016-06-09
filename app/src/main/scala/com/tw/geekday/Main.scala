package com.tw.geekday

import com.tw.geekday.framework.Assembly

object Main extends App {
  new Assembly(args.head, "tw-kafka-geekday").pipeline.run()
}
