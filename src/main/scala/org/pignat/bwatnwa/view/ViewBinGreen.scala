package org.pignat.bwatnwa.view

class ViewBinGreen extends View {
  
  def draw(g:Graphics, data:Array[Byte]) : Unit = {
    g.draw(data, g.color(0,1,0))
  }

}