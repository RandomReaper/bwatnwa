package org.pignat.bwatnwa

import processing.core.PApplet

class Embedded extends PApplet with ByteArrayEater {

  override def setData(b:Array[Byte]) : Unit = {}
  
  override def setup(): Unit = {
    // original setup code here ...
    size(800, 800)

    // prevent thread from starving everything else
    background(scala.util.Random.nextInt)
    noLoop()
  }

  override def draw(): Unit = {
    stroke(255)
    if (mousePressed) {
      line(mouseX, mouseY, pmouseX, pmouseY)
    }
  }

  override def mousePressed(): Unit = {
    // do something based on mouse movement

    // update the screen (run draw once)
    redraw()
  }
}