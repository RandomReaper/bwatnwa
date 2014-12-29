package org.pignat.bwatnwa.util

import java.nio.ByteBuffer

object Utils {
  def round2power2(n: Int): Int = {
    var v = n
    v = v - 1
    v |= v >> 1
    v |= v >> 2
    v |= v >> 4
    v |= v >> 8
    v |= v >> 16
    v = v + 1
    if (v > n) return v/2
     
    return v
  }
  
  def scale(b:Array[Byte], mul: Int, div: Int) : Array[Byte] = {
    val big = Array.fill[Byte](b.length*mul)(0)
    val result = Array.fill[Byte](b.length*mul/div)(0)
    
    if (div == 1 && mul == 1) {
      return b
    }
    
    for (i <- 0 until b.length) {
      for (j <- 0 until mul) {
        big(i*mul+j) = b(i)
      }  
    }


    for (i <- 0 until result.length)
    {
      var sum = 0
      for (j <- 0 until div)
      {
        sum += big(i*div+j)
      }
      result(i) = (sum/div).toByte
    }
    
    return result
  }
  
  def scaleFactor(b:Array[Byte], target:Int) : (Int, Int) = {
    if (b.length <= target)
    {
      return (target/b.length, 1)
    }
    else
    {
      return (1, (b.length+target-1)/target)  
    }   
  }
  
  def scaleToSize(b:Array[Byte], target:Int) : Array[Byte] = {
    val x = scaleFactor(b, target)
    return scale(b, x._1, x._2)
  }
  
  //rotate/flip a quadrant appropriately
  def rot(n:Int, sx:Int, sy:Int, rx:Int, ry:Int) : (Int, Int) = {
    var x = sx
    var y = sy
    
    if (ry == 0) {
        if (rx == 1) {
            x = n-1 - x
            y = n-1 - y
        }
 
        //Swap x and y
        val t  = x
        x = y
        y = t
    }
    return (x, y)
}
  
  //convert (x,y) to d
  def lin2hilbert2d(n:Int, d:Int): (Int, Int) = {
      var rx:Int = 0
      var ry:Int = 0
      var s:Int = 0
      var t=d
      var x:Int = 0
      var y:Int = 0

      s = 1
      while (s < n) {
        rx = 1 & (t/2)
        ry = 1 & (t ^ rx)
        val a = rot(s, x, y, rx, ry)
        x = a._1
        y = a._2
        x += s * rx
        y += s * ry
        t /= 4
        
        s *=2
      }
      
      return (x, y)
  }

  def hilbert2d2lin(n:Int, sx:Int, sy:Int) : Int = {
    var x=sx
    var y=sy
    var rx:Int=0
    var ry:Int=0
    var s:Int=0
    var d:Long=0
    
    s=n/2
    while (s>0)
    {
      rx = if ((x & s) > 0) 1 else 0
      ry = if ((y & s) > 0) 1 else 0
      d += s * s * ((3 * rx) ^ ry)
      val a = rot(s, x, y, rx, ry)
      x = a._1
      y = a._2
      s/=2
    }

    return d.toInt
  }
}