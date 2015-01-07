package org.pignat.bwatnwa

import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import org.pignat.bwatnwa.util.BinaryFileReader
import javax.swing.JFrame
import javax.swing.JSplitPane
import java.awt.BorderLayout
import javax.swing.JPanel
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JButton
import javax.swing.JDialog
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import org.pignat.bwatnwa.view._
import org.pignat.bwatnwa.transform._

class MainWindow extends JFrame("BwatNwa") with ByteArrayEater with PointListener {

  var data:Array[Byte] = null
  val ndisplay = new TransformView(this)

  def point(s:Int) = {
    navigator.point(s)
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    ndisplay.setData(data)
  }
  
  val navigator = new NavigatorPanel(this)
  navigator.setPreferredSize(new Dimension(128, 512))
  navigator.setSize(new Dimension(128, 512))
  navigator.setData(BinaryFileReader.fromResource("bash"))

  setLayout(new BorderLayout)
  add(navigator, BorderLayout.WEST)
  ndisplay.setPreferredSize(new Dimension(512, 512))  
  add(ndisplay, BorderLayout.CENTER)
  add(ndisplay, BorderLayout.CENTER)
  ndisplay.set(new TransformBin, new ViewHilbert2d, data)

  val buttonPanel = new JPanel
  buttonPanel.setLayout(new GridLayout(10,1))
  
  val buttons = List(
    (
     "Hiblert blue",
     new ActionListener() { def actionPerformed(e:ActionEvent) = ndisplay.set(new TransformBin, new ViewHilbert2d, data) }
    ),
    (
     "Hexdump",
     new ActionListener() { def actionPerformed(e:ActionEvent) = ndisplay.set(new TransformBin, new ViewHexdump, data) }
    ),
    (
     "Digraph",
     new ActionListener() { def actionPerformed(e:ActionEvent) = ndisplay.set(new TransformDigraph, new ViewDigraph, data) }
    ),
    (
     "Binary red",
     new ActionListener() { def actionPerformed(e:ActionEvent) = ndisplay.set(new TransformBin, new ViewBinRed, data) }
    ) 
  )
  
  buttons.foreach { x=>
    val b = new JButton(x._1)
    b.addActionListener(x._2)
    buttonPanel.add(b)
  }
 
  add(buttonPanel, BorderLayout.EAST)
  
  pack()

  addWindowListener(new WindowAdapter() {
    override def windowClosing(we: WindowEvent) {
      ndisplay.dispose()
      dispose()
    }
  })
}
