package org.pignat.bwatnwa.view

import processing.core.PGraphics
import org.pignat.bwatnwa.util._

class ViewDigraph extends View2d {
  
  def draw(g:PGraphics, data:Array[Array[Byte]]) : Unit = {
    Graphics.draw(g, data, g.color(1,1,1))
  }

  def getPoint(x:Int, y:Int, sizex:Int, sizey:Int, data:Array[Byte]) : Int = {
    return 0
  }
  
  def size(g:PGraphics) : (Int, Int) = (256,256)
}