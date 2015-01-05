package org.pignat.bwatnwa

import org.pignat.bwatnwa.util.Utils

abstract class GraphicalViewAsync extends GraphicalView {
  
  override def setup(): Unit = {
    val s = getSize
    size(512,512,processing.core.PConstants.P3D);
    frameRate(210)
  }

  def handle_resize(x:Int, y:Int) : Unit
  def handle_update() : Unit
  def worker_started() : Unit
  def worker_done() : Unit
  
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
}