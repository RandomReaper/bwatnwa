/**
 * conti's digraph
 */
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
    val s = data.slice(0, 16).map(x=>f"$x%02x")
    val a = data.slice(0, 16).map(x=>f"$x%c")
    val t = s.mkString(" ") + " | " + a.mkString("") + " | "
    println(s"t=$t")
    
    noLoop
  }
}