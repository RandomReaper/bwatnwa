package org.pignat.bwatnwa.transform

import org.pignat.bwatnwa.Transform1d
import org.pignat.bwatnwa.util.Utils

class TransformNone extends Transform1d {
 
  def run() : Unit = {
    System.arraycopy(source, 0, destination, 0, Math.min(source.length, destination.length))
  } 
}