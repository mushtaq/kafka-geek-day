package com.tw.geekday

import com.tw.geekday.framework.{AbstractAssembly, ConnectionParams}

class TestAssembly(serviceName: String, _connectionParams: ConnectionParams) extends AbstractAssembly("dev", serviceName) {
  lazy val connectionParams = _connectionParams
}
