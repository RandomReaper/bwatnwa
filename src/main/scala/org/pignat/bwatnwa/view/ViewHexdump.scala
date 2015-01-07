package org.pignat.bwatnwa.view

import processing.core.PGraphics
import org.pignat.bwatnwa.util._

class ViewHexdump extends View1d {
    
  val margin = 5
  val textHeight = 12
  val lineWidth = 16  
  
  def draw(g:PGraphics, data:Array[Byte]) : Unit = {
    
    if (data == null) return
   
    val dataSize = (g.height - 2 * margin / textHeight) * lineWidth
    val ldata = data.slice(0, dataSize)
    
    g.background(50)
    g.fill(200)
    g.textSize(textHeight)

    for (i <- 0 until g.height - 2 * margin / textHeight) {
      var t = ""
      for (j <- 0 until lineWidth ) {
        if (i*lineWidth+j < data.length) {
          t += f"${data(i*lineWidth+j)}%02x "    
        }
        else {
          t += "   "
        }
      }
      
      t += "| "
      
      for (j <- 0 until lineWidth ) {
        if (i*lineWidth+j < data.length) {
          val c = data(i*lineWidth+j) & 0xff
          if (c >= ' ' && c <= '~')
          {
            t += f"$c%c"
          }
          else
          {
            t += "."
          }
              
        }
        else {
          t += " "
        }
      }
      t += " |"
      
      // Not a fixed width font, take this ;) 
      for (k <- 0 until t.length) {
        g.text(t(k), margin+k*textHeight*3/5, margin+i*textHeight)
      }
    }
  }

  def getPoint(x:Int, y:Int, sizex:Int, sizey:Int, data:Array[Byte]) : Int = {
    return 0
  }
  
  def size(g:PGraphics) : Int = (g.height - 2 * margin / textHeight) * lineWidth
}