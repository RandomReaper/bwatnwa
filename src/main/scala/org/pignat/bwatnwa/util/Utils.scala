package org.pignat.bwatnwa.util

import java.nio.ByteBuffer
import org.pignat.bwatnwa.GraphicalView

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
  
  def scaleMul(b:Array[Byte], mul:Int) : Array[Byte] = {
    require(mul >= 1)
    
    if (mul == 1) return b
    
    val r = Array.ofDim[Byte](b.length*mul)
    
    for (i <- 0 until b.length) {
      for (j <- 0 until mul) {
        r(i*mul+j) = b(i)
      }  
    }
    
    return r
  }
  
  def scaleMul(b:Array[Array[Byte]], mul:Int) : Array[Array[Byte]] = {
    require(mul >= 1)
    
    if (mul == 1) return b
    
    val r = Array.ofDim[Byte](b.length*mul, b(0).length*mul)
    
    for (x <- 0 until b.length; y <- 0 until b(0).length)
    {
      for (j <- 0 until mul) {
        r(x*mul+j)(y*mul+j) = b(x)(y)
      }
    }
    
    return r
  }

  def scaleDiv(b:Array[Byte], div:Int) : Array[Byte] = {
    require(div >= 1)
    
    if (div == 1) return b
    
    val r = Array.ofDim[Byte](b.length/div)

    for (i <- 0 until r.length)
    {
      var sum = 0
      for (j <- 0 until div)
      {
        sum += b(i*div+j) & 0xff
      }
      r(i) = (sum/div).toByte
    }
    
    return r
  }
  
  def scaleDiv(b:Array[Array[Byte]], div:Int) : Array[Array[Byte]] = {
    require(div >= 1)
    
    if (div == 1) return b
    
    val r = Array.ofDim[Byte](b.length/div, b(0).length/div)

    for (x <- 0 until r.length; y <- 0 until r(0).length)
    {
      var sum = 0
      for (j <- 0 until div)
      {
        sum += b(x*div+j)(y*div+j) & 0xff
      }
      r(x)(y) = (sum/div).toByte
    }
    
    return r
  }

  def scale(b:Array[Byte], muldiv:(Int, Int)) : Array[Byte] = {
    return scaleDiv(scaleMul(b,muldiv._1),muldiv._2)
  }
 
  def scale(b:Array[Array[Byte]], muldiv:(Int, Int)) : Array[Array[Byte]] = {
    return scaleDiv(scaleMul(b,muldiv._1),muldiv._2)
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
  
  def scaleFactor(b:Array[Array[Byte]], target:(Int,Int)) : (Int, Int) = {
    val x = b.length
    val y = b(0).length
    
    val scale_x = target._1.toDouble/x 
    val scale_y = target._2.toDouble/y  
  
    if (scale_x >= 1 && scale_y >= 1)
    {
      return (Math.min(target._1/x, target._2/y), 1)
    }
    else
    {
      return (1, Math.max((x+target._1-1)/target._1, (y+target._2-1)/target._2))
    }
  }
  
  def scaleToSize(b:Array[Byte], target:Int) : Array[Byte] = {
    val x = scaleFactor(b, target)
    return scale(b, x)
  }
  
  def scaleToSize(b:Array[Array[Byte]], target:(Int,Int)) : Array[Array[Byte]]= {
    val x = scaleFactor(b, target)
    return scale(b, x)
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