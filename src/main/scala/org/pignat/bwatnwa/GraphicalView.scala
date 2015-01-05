package org.pignat.bwatnwa

import processing.core.PApplet
import java.awt.event.WindowAdapter
import com.sun.glass.events.WindowEvent
import org.pignat.bwatnwa.util.Utils

class GraphicalView extends PApplet {
  init()
  
  def draw(b:Array[Byte], color:Int) {
    val xwidth = Utils.round2power2(width)
    val d = Utils.scaleToSize(b, xwidth * height)
    loadPixels
    if (width == xwidth) {
      for (i <- 0 until d.length)
      {
        pixels(i) = color*(d(i) & 0xff)
      }
    }
    else
    {
      for (i <- 0 until d.length)
      {
        val x = i % xwidth
        val y = i / xwidth
        pixels(y*width+x) = color*(d(i) & 0xff)
      }      
    }
    
    updatePixels
  }

  def draw(b:Array[Array[Byte]], color:Int) {
    val d = Utils.scaleToSize(b, (width, height))
    
    loadPixels
    
    for (x <- 0 until d.length; y <- 0 until d(0).length)
    {
      pixels(y*width+x) = color*(d(x)(y) & 0xff)
    }
    
    updatePixels
  }
}