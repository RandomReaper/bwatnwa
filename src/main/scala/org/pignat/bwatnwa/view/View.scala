package org.pignat.bwatnwa.view

import processing.core.PGraphics
import org.pignat.bwatnwa.util.Utils

object Graphics {
  def draw(g:PGraphics, b:Array[Byte], color:Int) {
    val xwidth = Utils.round2power2(g.width)
    val d = Utils.scaleToSize(b, xwidth * g.height)
    
    //println(s"g.width= ${g.width}xwidth=$xwidth")

    if (false && g.width == xwidth) {
      g.loadPixels
      for (i <- 0 until d.length)
      {
        g.pixels(i) = color*(d(i) & 0xff)
      }
      g.updatePixels
    }
    else
    {
      //g.loadPixels
      require(d.length <= g.width * g.height)
      for (i <- 0 until d.length)
      {

        val x = i % xwidth
        val y = i / xwidth
        
        g.set(x, y, color*(d(i) & 0xff))
        //g.pixels(y*g.width+x) = color*(d(i) & 0xff)
      }
      //g.updatePixels
    }   

  }

  def draw(g:PGraphics, b:Array[Array[Byte]], color:Int) {
    val d = Utils.scaleToSize(b, (g.width, g.height))
    
    //g.loadPixels
    
    for (x <- 0 until Utils.round2power2(d.length); y <- 0 until Utils.round2power2(d(0).length))
    {
      g.set(x, y, color*(d(x)(y) & 0xff))
      g.pixels(y*g.width+x) = color*(d(x)(y) & 0xff)
    }
    
   //g.updatePixels
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

trait View1d {
  def draw(g:PGraphics, data:Array[Byte]) : Unit
  def getPoint(x:Int, y:Int, sizex:Int, sizey:Int, data:Array[Byte]) : Int
  def size(g:PGraphics) : Int
}

trait View2d {
  def draw(g:PGraphics, data:Array[Array[Byte]]) : Unit
  def getPoint(x:Int, y:Int, sizex:Int, sizey:Int, data:Array[Byte]) : Int
  def size(g:PGraphics) : (Int, Int)
}