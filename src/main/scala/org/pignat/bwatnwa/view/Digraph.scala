/**
 * conti's digraph
 */
package org.pignat.bwatnwa.view

import org.pignat.bwatnwa.util.Utils
import org.pignat.bwatnwa.GraphicalView
import org.pignat.bwatnwa.ByteArrayEater
import org.pignat.bwatnwa.PointListener

class Digraph extends GraphicalView with ByteArrayEater with PointListener {
  
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
  
    val digraph = Array.fill[Int](256,256)(0)
    (data.slice(0, data.length -1) zip data.slice(1, data.length)) foreach
    { x =>
      digraph(x._1 & 0xff)(x._2 & 0xff) += 1
    }

    val max = digraph.flatten.max
    val d = digraph.flatten.map(x => (Math.log(x)/Math.log(max) * 255).toByte)
    
    val ll = 256
    for (i <- 0 until d.length) {
      var x = i % ll
      var y = i / ll
      set(x,y,color(color(d(i) & 0xff)))
    }
    
    noLoop
  }
}