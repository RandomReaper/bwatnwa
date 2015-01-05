package org.pignat.bwatnwa.transform

import org.pignat.bwatnwa.Transform1d
import org.pignat.bwatnwa.util.Utils

class TransformBin extends Transform1d {
 
  def run() : Unit = {
    val tmp = Utils.scaleToSize(source, destination.length)
    System.arraycopy(tmp, 0, destination, 0, Math.min(tmp.length, destination.length))
  } 
}