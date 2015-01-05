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
  val ndisplay = new TransformView

  def point(s:Int) = {
    navigator.point(s)
  }
  
  def changeDisplay(g:GraphicalView with ByteArrayEater) : Unit = {
    val d = display.getSize 
    remove(display)
    display.dispose
    display = g
    display.setPreferredSize(d)
    display.setSize(d)
    display.setMinimumSize(new Dimension(512, 512))
    display.setPreferredSize(new Dimension(512, 512))
    add(display, BorderLayout.CENTER)
    pack()
    display.setData(data)
  }
  
  override def setData(b:Array[Byte]) : Unit = {
    data = b
    display.setData(data)
    ndisplay.setData(data)
  }
  
  var display:GraphicalView with ByteArrayEater = new Binary2dView(this)
  
  val navigator = new NavigatorPanel(this)
  navigator.setPreferredSize(new Dimension(128, 512))
  navigator.setSize(new Dimension(128, 512))
  navigator.setData(BinaryFileReader.fromResource("bash"))

  setLayout(new BorderLayout)
  add(navigator, BorderLayout.WEST)
  display.setPreferredSize(new Dimension(512, 512))
  add(display, BorderLayout.CENTER)
  ndisplay.set(new TransformBin, new ViewBinGreen, data)
  changeDisplay(ndisplay)
  val buttonPanel = new JPanel
  buttonPanel.setLayout(new GridLayout(10,1))
  
  val buttons = 0 to 9 map (x=>new JButton(x.toString))
  buttons(0).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new Binary2dViewDelta) 
    }
  });
  buttons(1).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new Binary2dView(MainWindow.this))
    }
  });
  buttons(2).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new Binary2dViewRed)
    }
  });
  buttons(3).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new Binary2dViewSquare(MainWindow.this))
    }
  });
  buttons(4).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new Digraph())
    }
  });
  buttons(5).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new Hexdump())
    }
  });
  buttons(6).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new AsyncTest())
    }
  });
  buttons(7).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      changeDisplay(new TestTransformView())
    }
  });
  buttons(8).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      ndisplay.set(new TransformBin, new ViewBinGreen, data)
    }
  });
  buttons(9).addActionListener(new ActionListener()
  {
    def actionPerformed(e:ActionEvent ) = {
      ndisplay.set(new TransformBin, new ViewBinRed, data)
    }
  });
  buttons.foreach(buttonPanel.add(_))
  
  add(buttonPanel, BorderLayout.EAST)
  
  pack()

  addWindowListener(new WindowAdapter() {
    override def windowClosing(we: WindowEvent) {
      display.dispose()
      dispose()
    }
  })
}
