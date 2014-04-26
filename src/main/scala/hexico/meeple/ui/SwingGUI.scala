package hexico.meeple.ui

import scala.swing._
import scala.swing.event.ButtonClicked
import javax.swing.WindowConstants
import hexico.meeple.game.Tilesets

object SwingGUI extends SimpleSwingApplication {
  val launch = new Button("Launch game")
  val examine = new Button("Examine tileset")

  listenTo(launch)
  reactions += { case ButtonClicked(`launch`) =>
    val frame = new GameWindow
    frame.peer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    frame.visible = true
  }

  listenTo(examine)
  reactions += { case ButtonClicked(`examine`) =>
    val frame = new ExamineWindow(Tilesets.START ++ Tilesets.BASE)
    frame.peer.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
    frame.visible = true
  }

  def top = new MainFrame {
    contents = new FlowPanel {
      contents += launch
      contents += examine
    }
  }
}
