package com.tw.testutils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.scalatest.{FunSuite, Matchers}

class KafkaSuite extends FunSuite with Matchers {
  def withRule[T <: TestRule](rule: T)(testCode: T => Any): Unit = {
    rule(
      new Statement() {
        override def evaluate(): Unit = testCode(rule)
      },
      Description.createSuiteDescription("JUnit rule wrapper")
    ).evaluate()
  }
}
