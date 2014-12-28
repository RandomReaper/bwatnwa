package org.pignat.bwatnwa

import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

import org.pignat.bwatnwa.util.BinaryFileReader

import javax.swing.JFrame
import javax.swing.JSplitPane

class MainWindow extends JFrame("BwatNwa") {

  val data = BinaryFileReader.fromResource("bash")

  val embed = List(new NavigatorPanel, new Embedded)
  embed foreach { x =>
    x.init()
    x.setSize(400, 400)
    x.setMinimumSize(new Dimension(400, 400))
  }
  
  val n2 = new NavigatorPanel2

  val splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, n2, embed(0))
  n2.setData(data)
  embed(0).setData(data)
  splitPane.setDividerLocation(.5)

  add(splitPane)
  pack()

  addWindowListener(new WindowAdapter() {
    override def windowClosing(we: WindowEvent) {
      embed foreach (_.dispose())
      dispose()
    }
  })
}
