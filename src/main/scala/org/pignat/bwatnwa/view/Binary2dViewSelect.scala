package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalView
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener

class Binary2dViewSelect extends GraphicalView with ByteArrayEater with PointListener {
  
  var point = -1
  
  def point(s:Int) = {
    point = s
    loop
  }
  
  var dataEaters : Set[ByteArrayEater] = Set()
  var data:Array[Byte] = null;
  val selectBarWidth = 2;
  var select = (0, 0) 
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    select = (0, data.length)
    loop
    dataEaters.foreach(_.setData(data))
  }
  
  def addDataEater(e:ByteArrayEater) : Unit = {
   dataEaters += e 
  }
  
  def removeDataEater(e:ByteArrayEater) : Unit = {
   dataEaters -= e 
  }

  override def setup(): Unit = {

  }

  override def draw(): Unit = {
    if (data == null) return
    
    val xwidth = Utils.round2power2(width)
    val t = Utils.scaleToSize(data, xwidth*height-(2*selectBarWidth))
    background(color(30,0,0))
    
    for (i <- 0 until t.length) {
      var x = i % xwidth
      var y = selectBarWidth + i / xwidth
      set(x,y,color(0, t(i) & 0xff, 0))
    }
    
    val nr_lines = t.length/xwidth
    stroke(255)
    line(0, select._1*nr_lines/data.length, xwidth, select._1*nr_lines/data.length)
    line(0, select._2*nr_lines/data.length, xwidth, select._2*nr_lines/data.length)
    
    if (point != -1)
    {
      point += select._1
      stroke(255,0,0)
      line(0, point*nr_lines/data.length, width, point*nr_lines/data.length)
      point = -1
    }
    
    noLoop
  }

  override def mousePressed(): Unit = {
    // When sliding, mouse coordinates can be outside the window !
    mouseY = Math.min(mouseY, height)
    mouseY = Math.max(mouseY, 0)

    val xwidth = Utils.round2power2(width)
    val scale = Utils.scaleFactor(data, xwidth*height-(2*selectBarWidth))
    val g_select = (select._1 / (xwidth*scale._2), select._2 / (xwidth*scale._2))
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
    
    /**
     * Make sure selection is positive, in the data and size > 0
     */
    if (select._1 > select._2) {
      select = select.swap
    }
    
    select = (Math.max(select._1, 0), Math.min(select._2, data.length))
    
    if (select._1 == select._2) {
      if (select._2 + 1 < data.length ) {
        select = (select._1, select._2+1)
      }
      else if (select._1 - 1 >= 0 ) {
        select = (select._1-1, select._2)
      }
    }
    
    dataEaters.foreach(_.setData(data.slice(select._1, select._2)))
    
    loop
  }

  override def mouseDragged() = {
    mousePressed()
  }  
}