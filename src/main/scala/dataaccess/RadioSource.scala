package dataaccess

import java.io.InputStream
import java.net.URL

import javazoom.jl.player.Player
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}


class RadioSource(_sourceConfig: RadioSourceConfig) extends RichSourceFunction[Seq[Int]]{

  var _radioStream: InputStream = _
  var _player: Player = _
  var _recordingDevice: RecordingDevice = _

  override def open(parameters: Configuration): Unit = {

    _radioStream = _sourceConfig.streamUrl.openStream()
    _recordingDevice = new RecordingDevice(_sourceConfig.buffer)
    _player = new Player(_radioStream, _recordingDevice)
    Future { _player.play() } (ExecutionContext.global)
  }

  override def run(context: SourceFunction.SourceContext[Seq[Int]]): Unit = {

    while(!_player.isComplete){

      Thread.sleep(1000)
      context.collect(_sourceConfig.buffer)
      _recordingDevice.flush()
    }
  }

  override def cancel(): Unit = {

  }
}

case class RadioSourceConfig(streamUrl: URL, buffer: ListBuffer[Int])
