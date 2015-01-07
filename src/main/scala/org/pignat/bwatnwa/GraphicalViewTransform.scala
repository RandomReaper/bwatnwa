package org.pignat.bwatnwa

import org.pignat.bwatnwa.util.Utils

abstract class GraphicalViewTransform(pl:PointListener) extends GraphicalView with TransformListener {
  
  override def setup(): Unit = {
    val s = getSize
    size(513,512,processing.core.PConstants.P3D);
    //frameRate(60)
  }

  def handle_resize(x:Int, y:Int) : Unit
  def handle_update() : Unit
  def handle_point(x:Int, y:Int) : Int

  protected var m_width = width
  protected var m_height = height
  
  override def draw(): Unit =
  {
    if (width != m_width || height != m_height)
    {
      m_width = width
      m_height = height
      handle_resize(m_width, m_height)
    }
    
    handle_update()
  }

  override def mousePressed() = {
    pl.point(handle_point(Math.max(Math.min(mouseX, width), 0), Math.max(Math.min(mouseY, height), 0)))
  }
  
  override def mouseDragged() = {
    mousePressed()
  }  
}