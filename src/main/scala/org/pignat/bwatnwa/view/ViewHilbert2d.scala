package org.pignat.bwatnwa.view

import processing.core.PGraphics
import org.pignat.bwatnwa.util._

class ViewHilbert2d extends View1d {
  
  def draw(g:PGraphics, data:Array[Byte]) : Unit = {
    val xwidth = Utils.round2power2(Math.min(g.width,g.height))
    val t = Utils.scaleToSize(data, xwidth*xwidth)
    g.background(Graphics.color(127))
    
    for (i <- 0 until t.length) {
      var (x,y) = Utils.lin2hilbert2d(xwidth, i)
      g.set(x,y,Graphics.color(0, 0, t(i) &  0xff))
    }
  }

  def getPoint(x:Int, y:Int, sizex:Int, sizey:Int, data:Array[Byte]) : Int = {
    val xwidth = Utils.round2power2(Math.min(sizex,sizey))
    val (scale_mul, scale_div) = Utils.scaleFactor(data, xwidth*xwidth)
    return Math.min(Utils.hilbert2d2lin(xwidth, x, y) * scale_div/scale_mul, data.length)
  }
  
  def size(g:PGraphics) : Int = Utils.round2power2(Math.min(g.width, g.height)) * Utils.round2power2(Math.min(g.width, g.height))
}