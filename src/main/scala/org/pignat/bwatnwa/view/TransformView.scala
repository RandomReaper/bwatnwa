package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalViewTransform
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener
import org.pignat.bwatnwa.TransformManager
import org.pignat.bwatnwa.Transform1d
import org.pignat.bwatnwa.view._

class TransformView extends GraphicalViewTransform with ByteArrayEater with PointListener {
  
  var point = -1
  @volatile var done = false
  
  val transformManager = new TransformManager(this)
  
  var transform1d:Transform1d = null
  var view1d:View = null
  var source1d:Array[Byte] = null 
  var destination1d:Array[Byte] = null
  
  def TransformDone() : Unit = {
    done = true
  }

  def point(s:Int) = {
    point = s
    redraw
    loop
  }
  
  def set(t:Transform1d, v:View, b:Array[Byte]) {
    transform1d = t
    view1d = v
    setData(b)
  }
    
  override def setData(b:Array[Byte]) : Unit = {
    source1d = b
    handle_resize(width, height)
  }
  
  override def handle_resize(x:Int, y:Int) : Unit = {
    if (transform1d==null || source1d==null) return
    println(s"x=$x, y=$y, width=$width, height=$height")

    destination1d = Array.fill(Utils.round2power2(width)*height)(0)
    transformManager.stop()
    transform1d.set(source1d, destination1d)
    transformManager.start(transform1d)
    done = false
    redraw
    loop
  }
  
  override def handle_update(): Unit = {
 
    if (destination1d != null)
      view1d.draw(new Graphics(g), destination1d)
    if (done) noLoop
  }
}