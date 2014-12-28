package org.pignat.bwatnwa

import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.GridLayout
import java.awt.Dimension
import java.nio.MappedByteBuffer

class NavigatorPanel2 extends JPanel with ByteArrayEater {

  var data:Array[Byte] = null;

  override def setData(b:Array[Byte]) : Unit = {
    d.foreach { _.setData(b) }
  }
  
  setLayout(new BorderLayout)
  val sizeLabel = new JLabel("size")
  add(sizeLabel, BorderLayout.PAGE_START)
  
  val p =  new JPanel
  p.setLayout(new GridLayout(1,2))
  val d = List(new Binary2dViewSelect, new Binary2dView)
  d.foreach {
    x =>
      x.init()
      p.add(x)
  }
  
  add(p, BorderLayout.CENTER)
  
  setMinimumSize(new Dimension(64,64))
  setPreferredSize(new Dimension(64,64))
}