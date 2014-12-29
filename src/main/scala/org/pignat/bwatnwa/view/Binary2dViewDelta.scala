package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalView
import org.pignat.bwatnwa.ByteArrayEater

import processing.core.PApplet

class Binary2dViewDelta extends GraphicalView with ByteArrayEater {
  
  var data:Array[Byte] = null;
  
  override def setData(b:Array[Byte]) : Unit = {
    data = Array.ofDim[Byte](b.length)
    data(0) = 0
    for (i <- 1 until data.length) {
      data(i) = (b(i) - b(i-1)).toByte
    }
      
    redraw
    loop
  }
  
  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(width)
    val t = Utils.scaleToSize(data, xwidth*height)
    background(color(30,0,0))
    
    for (i <- 0 until t.length) {
      var x = i % xwidth
      var y = i / xwidth
      set(x,y,color(t(i) & 0xff))
    }
    
    noLoop
  }

  override def mousePressed(): Unit = {
    
  }
}