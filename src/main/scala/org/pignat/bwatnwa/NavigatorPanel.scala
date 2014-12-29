package org.pignat.bwatnwa

import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.GridLayout
import java.awt.Dimension
import java.nio.MappedByteBuffer
import org.pignat.bwatnwa.view.Binary2dViewSelect
import org.pignat.bwatnwa.view.Binary2dView

class NavigatorPanel(b:ByteArrayEater) extends JPanel with ByteArrayEater with PointListener {

  var data:Array[Byte] = null;

  def point(s:Int) = {
    zoom.point(s)
    navigation.point(s)
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    navigation.setData(b)
  }
  
  setLayout(new BorderLayout)
  val sizeLabel = new JLabel("size")
  add(sizeLabel, BorderLayout.PAGE_START)
  
  val p =  new JPanel
  p.setLayout(new GridLayout(1,2))
  val zoom = new Binary2dView(this)
  val navigation = new Binary2dViewSelect
  navigation.addDataEater(zoom)
  navigation.addDataEater(b)
  p.add(navigation)
  p.add(zoom)  
  add(p, BorderLayout.CENTER)
  
  setMinimumSize(new Dimension(64,64))
  setPreferredSize(new Dimension(64,64))
}
