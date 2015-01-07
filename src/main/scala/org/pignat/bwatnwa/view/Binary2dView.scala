package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalView
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener

class Binary2dView(pl:PointListener) extends GraphicalView with ByteArrayEater with PointListener {
  
  var data:Array[Byte] = null;
  var point = -1
  
  def point(s:Int) = {
    point = s
    loop
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    loop
  }
  
  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(width)
    val t = Utils.scaleToSize(data, xwidth*height)
    background(color(30,0,0))
    
    draw(data, color(0,1,0))
        
    if (point != -1)
    {
      var (scale_mul, scale_div) = Utils.scaleFactor(data, xwidth*height)
      var x = point % xwidth * scale_mul / scale_div
      var y = point / xwidth * scale_mul / scale_div
      stroke(255,0,0)
      line(0, y, width, y)
      point = -1
    }
    
    noLoop
  }

  override def mousePressed(): Unit = {
    val xwidth = Utils.round2power2(width)
    mouseX = Math.min(mouseX, width)
    mouseX = Math.max(mouseX, 0)
    mouseY = Math.min(mouseY, height)
    mouseY = Math.max(mouseY, 0)
    
    var (scale_mul, scale_div) = Utils.scaleFactor(data, xwidth*height)
    pl.point((mouseX+mouseY*xwidth)*scale_div/scale_mul)
  }
  
  override def mouseDragged() = {
    mousePressed()
  }  
}