package hexico.meeple.ui

import scala.swing._
import scala.swing.event.ButtonClicked
import javax.swing.WindowConstants

object SwingGUI extends SimpleSwingApplication {
  val launch = new Button("Launch game")

  listenTo(launch)
  reactions += { case ButtonClicked(`launch`) =>
    val frame = new GameWindow
    frame.peer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    frame.visible = true
  }

  def top = new MainFrame {
    contents = new FlowPanel {
      contents += launch
    }
  }
}
