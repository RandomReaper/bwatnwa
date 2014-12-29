package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalView
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener

class Binary2dViewSquare(pl:PointListener) extends GraphicalView with ByteArrayEater {
  
  var data:Array[Byte] = null;
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    redraw
    loop
  }
  
  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(Math.min(width,height))
    val t = Utils.scaleToSize(data, xwidth*xwidth)
    background(color(127))
    
    for (i <- 0 until t.length) {
      var (x,y) = Utils.lin2hilbert2d(xwidth, i)
      set(x,y,color(0, 0, t(i) &  0xff))
    }
    
    noLoop
  }

  override def mousePressed(): Unit = {
    val xwidth = Utils.round2power2(Math.min(width,height))
    mouseX = Math.min(mouseX, width)
    mouseX = Math.max(mouseX, 0)
    mouseY = Math.min(mouseY, height)
    mouseY = Math.max(mouseY, 0)
    val (scale_mul, scale_div) = Utils.scaleFactor(data, xwidth*xwidth)
    pl.point(Math.min(Utils.hilbert2d2lin(xwidth, mouseX, mouseY) * scale_div/scale_mul, data.length))
  }
  
  override def mouseDragged() = {
    mousePressed()
  }  
}