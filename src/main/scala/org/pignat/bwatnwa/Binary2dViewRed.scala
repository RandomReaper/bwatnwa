package org.pignat.bwatnwa

import org.pignat.bwatnwa.util.Utils

import processing.core.PApplet

class Binary2dViewRed extends GraphicalView with ByteArrayEater {
  
  var data:Array[Byte] = null;
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    redraw
    loop
  }
  
  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(width)
    val t = Utils.scaleToSize(data, xwidth*height)
    background(color(127))
    
    for (i <- 0 until t.length) {
      var x = i % xwidth
      var y = i / xwidth
      set(x,y,color(t(i), 0, 0))
    }
    
    noLoop
  }

  override def mousePressed(): Unit = {
    
  }
}