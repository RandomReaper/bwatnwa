package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalViewTransform
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener
import org.pignat.bwatnwa.TransformManager
import org.pignat.bwatnwa.Transform1d
import org.pignat.bwatnwa.Transform2d
import org.pignat.bwatnwa.view._

class TransformView(pl:PointListener) extends GraphicalViewTransform(pl) with ByteArrayEater with PointListener {
  
  var point = -1
  @volatile var done = false
  
  val transformManager = new TransformManager(this)
  
  var transform1d:Transform1d = null
  var view1d:View1d = null
  var source:Array[Byte] = null 
  var destination1d:Array[Byte] = null

  var transform2d:Transform2d = null
  var view2d:View2d = null
  var destination2d:Array[Array[Byte]] = null

  
  def TransformDone() : Unit = {
    done = true
  }

  def point(s:Int) = {
    point = s
    redraw
    loop
  }
  
  def set(t:Transform1d, v:View1d, b:Array[Byte]) {
    transform2d = null
    view2d = null
    destination2d = null
    transform1d = t
    view1d = v
    setData(b)
  }

  def set(t:Transform2d, v:View2d, b:Array[Byte]) {
    transform1d = null
    view1d = null
    destination1d = null
    transform2d = t
    view2d = v
    setData(b)
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    source = b
    handle_resize(width, height)
  }
  
  override def handle_resize(x:Int, y:Int) : Unit = {
    if (transform1d!=null && source!=null) {
      destination1d = Array.fill(view1d.size(g))(0)
      //println(s"destination1d.length=${destination1d.length}")
      transformManager.stop()
      transform1d.set(source, destination1d)
      transformManager.start(transform1d)
      done = false
    }

    if (transform2d!=null && source!=null) {
      destination2d = Array.fill[Byte](view2d.size(g)._1, view2d.size(g)._2)(0)
      //println(s"destination1d.length=${destination1d.length}")
      transformManager.stop()
      transform2d.set(source, destination2d)
      transformManager.start(transform2d)
      done = false
    }
    
    
    //println(s"x=$x, y=$y, width=$width, height=$height")

    loop
  }
  
  override def handle_update(): Unit =
  {
    if (done) noLoop
    
    if (destination1d != null)
    {
      view1d.draw(g, destination1d)
    }

    if (destination2d != null)
    {
      view2d.draw(g, destination2d)
    }
  }
  
  override def handle_point(x:Int, y:Int) : Int =
  {
    if (view1d != null)
    {
      return view1d.getPoint(x, y, width, height, source) : Int
    }
    
    if (view2d != null)
    {
      return view2d.getPoint(x, y, width, height, source) : Int
    }
    
    return 0
  }
}