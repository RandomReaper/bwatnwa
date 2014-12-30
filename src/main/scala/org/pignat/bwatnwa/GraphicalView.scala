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
    for (i <- 0 until d.length)
    {
      val x = i % width
      val y = i / width
      set(x,y, color*(d(i) & 0xff))
    }
  }

  def draw(b:Array[Array[Byte]], color:Int) {
    val d = Utils.scaleToSize(b, (width, height))
    
    for (x <- 0 until d.length; y <- 0 until d(0).length)
    {
      set(x,y, color*(d(x)(y) & 0xff))
    }
  }
}