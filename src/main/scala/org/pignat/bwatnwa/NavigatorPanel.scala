package org.pignat.bwatnwa

import processing.core.PApplet
import java.nio.MappedByteBuffer

class NavigatorPanel extends Tmp {

  var data:MappedByteBuffer = null;
  override def setData(b:MappedByteBuffer) : Unit = {
    data = b
  }
  
  override def setup(): Unit = {
    // original setup code here ...
    size(800, 800)

    // prevent thread from starving everything else
    noLoop()
  }

  override def draw(): Unit = {
    val sz = getSize
    loadPixels()
    (0 until pixels.length).foreach{
      x => 
      pixels(x) = color(0, data.get(x), 0)
    }
    
    updatePixels() 
  }

  override def mousePressed(): Unit = {
    // do something based on mouse movement

    // update the screen (run draw once)
    redraw()
  }
}