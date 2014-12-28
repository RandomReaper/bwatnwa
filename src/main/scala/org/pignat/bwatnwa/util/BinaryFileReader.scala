package org.pignat.bwatnwa.util

import java.nio.file.Paths
import java.io.FileInputStream
import java.io.File
import java.nio.channels.FileChannel.MapMode._
import java.nio.MappedByteBuffer
import java.nio.ByteBuffer

object BinaryFileReader {
  def fromResource(name: String) : Array[Byte] = {
    val file = new File(Paths.get(getClass().getResource("/" + name).toURI()).toString())
    val fileSize = file.length
    val stream = new FileInputStream(file)
    val result = new Array[Byte](file.length.toInt)
    val bb = ByteBuffer.wrap(result);
    stream.getChannel.read(bb);
   
    return result
  }
}