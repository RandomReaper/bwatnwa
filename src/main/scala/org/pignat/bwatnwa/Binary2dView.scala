package org.pignat.bwatnwa

import org.pignat.bwatnwa.util.Utils

import processing.core.PApplet

class Binary2dView extends PApplet with ByteArrayEater {
  
  var data:Array[Byte] = null;
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    redraw
  }
  
  override def setup(): Unit = {
    noLoop()
  }

  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(width)/2
    val t = Utils.scaleToSize(data, xwidth*height)
    background(color(30,0,0))
    
    for (i <- 0 until t.length) {
      var x = i % xwidth
      var y = i / xwidth
      set(x,y,color(0, t(i), 0))
    }
  }

  override def mousePressed(): Unit = {
  }
}