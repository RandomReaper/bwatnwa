package org.pignat.bwatnwa

import processing.core.PApplet
import javax.swing.JFrame
import java.awt.Dimension
import javax.swing.JSplitPane
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.FileInputStream
import java.nio.channels.FileChannel.MapMode._
import java.nio.file.Paths
import org.pignat.bwatnwa.util.BinaryFileReader

class MainWindow extends JFrame("BwatNwa") {

  val data = BinaryFileReader.fromResource("bash")

  val embed = List(new NavigatorPanel, new Embedded)
  embed foreach { x =>
    x.init()
    x.setSize(400, 400)
    x.setMinimumSize(new Dimension(400, 400))
  }

  val splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, embed(0), embed(1))
  embed(0).setData(data)
  splitPane.setDividerLocation(0.25)

  add(splitPane)
  pack()

  addWindowListener(new WindowAdapter() {
    override def windowClosing(we: WindowEvent) {
      embed foreach (_.dispose())
      dispose()
    }
  })
}
