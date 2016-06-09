package com.tw

import com.sksamuel.avro4s._

object GenericTypeExtensions {

  implicit class RichGenericType[T](item: T) {
    def toGenericRecord(implicit toRecord: ToRecord[T], fromRecord: FromRecord[T]) = RecordFormat[T].to(item)
  }

}
