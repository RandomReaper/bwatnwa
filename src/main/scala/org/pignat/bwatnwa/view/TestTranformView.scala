package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalViewTransform
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener
import org.pignat.bwatnwa.TransformManager
import org.pignat.bwatnwa.Transform1d

class TestTransformView extends GraphicalViewTransform with ByteArrayEater with PointListener {
  
  var source:Array[Byte] = null;
  var destination:Array[Byte] = null;
  var point = -1
  @volatile var done = false
  
  val transformManager = new TransformManager(this)
  
  def TransformDone() : Unit = {
    done = true
  }

  def point(s:Int) = {
    point = s
    redraw
    loop
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    source = b
    handle_resize(width, height)
  }
  
  override def handle_resize(x:Int, y:Int) : Unit = {
    destination = Array.fill(width*height)(0)
    transformManager.start(new Transform1d()
    {
      def run : Unit = {
        val finish_time = System.currentTimeMillis() + 2*1000
        while (!should_stop())
        {
          for (i <- 0 until destination.length)
          {
            destination(i) = System.currentTimeMillis().toByte
            if (should_stop() ||  System.currentTimeMillis() - finish_time > 0)
            {
              return
            }
          }
        }
      }
    })
    done = false
    redraw
    loop
  }
  
  override def handle_update(): Unit = {
 
    if (destination == null) return
    if (done) noLoop
    
    draw(destination, color(1,1,1))
  }
}