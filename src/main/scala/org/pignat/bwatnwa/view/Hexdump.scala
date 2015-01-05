package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalView
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener

class Hexdump extends GraphicalView with ByteArrayEater with PointListener {
  
  var data:Array[Byte] = null;
  var point = -1
  
  def point(s:Int) = {
    point = s
    redraw
    loop
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    redraw
    loop
  }
  
  override def draw(): Unit = {
    
    if (data == null) return
    
    val margin = 5
    val textHeight = 12
    val lineWidth = 16
    
    val dataSize = (height - 2 * margin / textHeight) * lineWidth
    val ldata = data.slice(0, dataSize)
    
    background(50)
    fill(200)
    textSize(textHeight)

    for (i <- 0 until height - 2 * margin / textHeight) {
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
        text(t(k), margin+k*textHeight*3/5, margin+i*textHeight)
      }
    }
    
    noLoop
  }
}