package org.pignat.bwatnwa.view

import processing.core.PGraphics
import org.pignat.bwatnwa.util.Utils

class Graphics(g:PGraphics) {
  def draw(b:Array[Byte], color:Int) {
    val xwidth = Utils.round2power2(g.width)
    val d = Utils.scaleToSize(b, xwidth * g.height)
    g.loadPixels
    if (g.width == xwidth) {
      for (i <- 0 until d.length)
      {
        g.pixels(i) = color*(d(i) & 0xff)
      }
    }
    else
    {
      for (i <- 0 until d.length)
      {
        val x = i % xwidth
        val y = i / xwidth
        g.pixels(y*g.width+x) = color*(d(i) & 0xff)
      }      
    }
    
    g.updatePixels
  }

  def draw(b:Array[Array[Byte]], color:Int) {
    val d = Utils.scaleToSize(b, (g.width, g.height))
    
    g.loadPixels
    
    for (x <- 0 until d.length; y <- 0 until d(0).length)
    {
      g.pixels(y*g.width+x) = color*(d(x)(y) & 0xff)
    }
    
    g.updatePixels
  }

  final def color(r:Int, g:Int, b:Int, a:Int) : Int = {

    return r << 16 | g << 8 | b << 0 | a << 24
  }
  
  final def color(r:Int, g:Int, b:Int) : Int = {
    return color(r, g, b, 0)
  }

  final def color(gray:Int) : Int = {
    return color(gray, gray, gray)
  }
}

trait View {
  def draw(g:Graphics, data:Array[Byte]) : Unit
}