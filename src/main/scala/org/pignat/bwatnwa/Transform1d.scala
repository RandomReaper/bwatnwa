package org.pignat.bwatnwa

abstract class Transform1d extends Transform {
  var source:Array[Byte] = null
  var destination:Array[Byte] = null
  
  def set(s:Array[Byte], d:Array[Byte]) : Unit = {
    source = s
    destination = d
  }
}
