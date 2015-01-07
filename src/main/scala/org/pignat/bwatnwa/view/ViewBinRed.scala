package org.pignat.bwatnwa.view

import processing.core.PGraphics
import org.pignat.bwatnwa.util._

class ViewBinRed extends View1d {
  
  def draw(g:PGraphics, data:Array[Byte]) : Unit = {
    Graphics.draw(g, data, g.color(1,0,0))
  }

  def getPoint(x:Int, y:Int, sizex:Int, sizey:Int, data:Array[Byte]) : Int = {
    val xwidth = Utils.round2power2(sizex)
    val scale = Utils.scaleFactor(data, xwidth*sizey)

    return Math.min(data.length, (y*sizex*scale._2/scale._1)+x*scale._2/scale._1)
  }
  
  def size(g:PGraphics) : Int = Utils.round2power2(g.width) * g.height
}