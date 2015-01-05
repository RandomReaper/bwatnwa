package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalViewAsync
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener
import org.pignat.bwatnwa.Worker

class AsyncTest extends GraphicalViewAsync with ByteArrayEater with PointListener {
  
  var source:Array[Byte] = null;
  var destination:Array[Byte] = null;
  var point = -1
  @volatile var done = false
  @volatile var started = false
  
  var worker = new Worker(this)
  
  def worker_done() : Unit = {
    done = true
  }

  def worker_started() : Unit = {
    started = true
  }
  
  def point(s:Int) = {
    point = s
    redraw
    loop
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    source = b
    destination = Array.fill(width*height)(0)
    started = false
    worker.setSource(source)
    worker.setDestination(destination)
    done = false
    waitStarted()
    redraw
    loop
  }
  
  def waitStarted() : Unit = {
      while (!started) {
        Thread.sleep(1)
      } 
  }
  
  override def handle_resize(x:Int, y:Int) : Unit = {
    destination = Array.fill(width*height)(0)
    started = false
    worker.setDestination(destination)
    done = false
    waitStarted()
    redraw
    loop
  }
  
  override def handle_update(): Unit = {
 
    if (destination == null) return
    if (done) noLoop
    
    draw(destination, color(1,1,1))
  }
}