package org.pignat.bwatnwa

import processing.core.PApplet

class NavigatorPanel extends PApplet with ByteArrayEater {

  var data:Array[Byte] = null;
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
  }
  
  override def setup(): Unit = {
    noLoop()
  }

  override def draw(): Unit = {
    val sz = getSize
    loadPixels()
    if (data == null) return
    (0 until pixels.length).foreach{
      x => 
      pixels(x) = color(0, data(x), 0)
    }
    
    updatePixels() 
  }

  override def mousePressed(): Unit = {
  }
}