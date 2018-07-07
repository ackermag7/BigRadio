package org.ackermag.bigradio

import java.net.URL

import dataaccess.{RadioSource, RadioSourceConfig}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

import scala.collection.mutable.ListBuffer


object JobRunner {

  def main(args: Array[String]) {

    val streamEnv = StreamExecutionEnvironment.getExecutionEnvironment

    val radioSource = new RadioSource(RadioSourceConfig(new URL("http://wbgo.streamguys.net/wbgo128"), new ListBuffer[Int]()))

    val stream = streamEnv.addSource(radioSource )

    stream.print()

    streamEnv.execute("Big Radio")
  }
}
