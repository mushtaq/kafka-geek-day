package com.tw

import com.sksamuel.avro4s._
import org.apache.avro.generic.GenericRecord

object GenericRecordExtensions {

  implicit class RichGenericRecord(record: GenericRecord) {
    def as[T: ToRecord : FromRecord] = RecordFormat[T].from(record)
  }

}
