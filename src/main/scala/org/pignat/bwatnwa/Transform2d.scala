package org.pignat.bwatnwa

abstract class Transform2d extends Transform {
  var source:Array[Byte] = null
  var destination:Array[Array[Byte]] = null
  
  def set(s:Array[Byte], d:Array[Array[Byte]]) : Unit = {
    source = s
    destination = d
  }
}
