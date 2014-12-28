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
      var sum = 0;
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
}