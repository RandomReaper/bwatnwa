package org.pignat.bwatnwa

import org.pignat.bwatnwa.util.Utils

import processing.core.PApplet

class Binary2dViewSelect extends PApplet with ByteArrayEater {
  
  var data:Array[Byte] = null;
  val selectBarWidth = 2;
  var select = (50000, 100000) 
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    select = (Math.min(select._1, data.length), Math.min(select._2, data.length))
    redraw
    loop
  }
  
  override def setup(): Unit = {

  }

  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(width)/2
    val t = Utils.scaleToSize(data, xwidth*height-(2*selectBarWidth))
    background(color(30,0,0))
    
    for (i <- 0 until t.length) {
      var x = i % xwidth
      var y = selectBarWidth + i / xwidth
      set(x,y,color(0, t(i), 0))
    }
    
    val nr_lines = t.length/xwidth
    stroke(255)
    line(0, select._1*nr_lines/data.length, width, select._1*nr_lines/data.length)
    line(0, select._2*nr_lines/data.length, width, select._2*nr_lines/data.length)
    noLoop
  }

  override def mousePressed(): Unit = {
    val xwidth = Utils.round2power2(width)/2
    val scale = Utils.scaleFactor(data, xwidth*height-(2*selectBarWidth))
    val g_select = (select._1 / (xwidth*scale._2), select._2 / (xwidth*scale._2))
    println(s"g_select = $g_select")
    println(s"mouseY = $mouseY")
    val dist_b = Math.abs(g_select._1 - mouseY)
    val dist_e = Math.abs(g_select._2 - mouseY)
    
    if (dist_b < dist_e)
    {
      select = (mouseY*xwidth*scale._2, select._2)
    }
    else
    {
      select = (select._1, mouseY*xwidth*scale._2)
    }
    
    redraw
    loop
  }
}