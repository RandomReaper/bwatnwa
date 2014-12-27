package org.pignat.bwatnwa.util

import java.nio.file.Paths
import java.io.FileInputStream
import java.io.File
import java.nio.channels.FileChannel.MapMode._
import java.nio.MappedByteBuffer

object BinaryFileReader {
  def fromResource(name: String) : MappedByteBuffer = {
    val file = new File(Paths.get(getClass().getResource("/" + name).toURI()).toString())
    val fileSize = file.length
    val stream = new FileInputStream(file)
    return stream.getChannel.map(READ_ONLY, 0, fileSize)
  }
}