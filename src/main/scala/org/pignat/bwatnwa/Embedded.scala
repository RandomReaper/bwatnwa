package org.pignat.bwatnwa

import processing.core.PApplet

class Embedded extends Tmp {

  override def setup(): Unit = {
    // original setup code here ...
    size(800, 800)

    // prevent thread from starving everything else
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