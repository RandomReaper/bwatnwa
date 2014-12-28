package org.pignat.bwatnwa

import org.pignat.bwatnwa.util.Utils

class Binary2dViewSelect() extends GraphicalView with ByteArrayEater {
  
  var dataEaters : Set[ByteArrayEater] = Set()
  var data:Array[Byte] = null;
  val selectBarWidth = 2;
  var select = (0, 0) 
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    dataEaters.foreach(_.setData(data))
    select = (0, data.length)
    redraw
    loop
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
      set(x,y,color(0, t(i), 0))
    }
    
    val nr_lines = t.length/xwidth
    stroke(255)
    line(0, select._1*nr_lines/data.length, xwidth, select._1*nr_lines/data.length)
    line(0, select._2*nr_lines/data.length, xwidth, select._2*nr_lines/data.length)
    noLoop
  }

  override def mousePressed(): Unit = {
    mouseY = Math.min(mouseY, height)
    mouseY = Math.max(mouseY, 0)
    val xwidth = Utils.round2power2(width)
    println(s"width:$width xwidth:$xwidth")
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
    
    dataEaters.foreach(_.setData(data.slice(select._1, select._2)))
    
    redraw
    loop
  }

  override def mouseDragged() = {
    mousePressed()
  }  
}