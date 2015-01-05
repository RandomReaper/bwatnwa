package org.pignat.bwatnwa.view

class ViewBinRed extends View {
  
  def draw(g:Graphics, data:Array[Byte]) : Unit = {
    g.draw(data, g.color(1,0,0))
  }

}