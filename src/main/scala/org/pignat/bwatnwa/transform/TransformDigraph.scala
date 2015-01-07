package org.pignat.bwatnwa.transform

import org.pignat.bwatnwa.Transform2d
import org.pignat.bwatnwa.util.Utils

class TransformDigraph extends Transform2d {
 
  def run() : Unit = {
    val digraph = Array.fill[Int](256,256)(0)
    (source.slice(0, source.length -1) zip source.slice(1, source.length)) foreach
    { x =>
      digraph(x._1 & 0xff)(x._2 & 0xff) += 1
    }

    val max = digraph.flatten.max
    val d = digraph.flatten.map(x => (Math.log(x)/Math.log(max) * 255).toByte)
    val x = digraph.map(_.map(x => (Math.log(x)/Math.log(max) * 255).toByte))
    
    val tmp = Utils.scaleToSize(x, (destination.length,destination(0).length))

    for (i <- 0 until tmp.length)
    {
      System.arraycopy(tmp(i), 0, destination(i), 0, tmp(i).length)
    }
  } 
}