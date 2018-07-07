package dataaccess

import javazoom.jl.decoder.Decoder
import javazoom.jl.player.AudioDevice

import scala.collection.mutable.ListBuffer


class RecordingDevice(_buffer: ListBuffer[Int]) extends AudioDevice{

  var _decoder: Decoder = _
  var _position: Int = _

  override def open(decoder: Decoder): Unit = {

    _decoder = decoder
  }

  override def isOpen: Boolean = {

    _position = 0
    true
  }

  override def write(samples: Array[Short], offs: Int, len: Int): Unit = {

    _buffer ++= samples.map(_.toInt)
    _position = _position + len
  }

  override def close(): Unit = {

  }

  override def flush(): Unit = {

    _buffer.clear()
  }

  override def getPosition: Int = {

    _position
  }
}
