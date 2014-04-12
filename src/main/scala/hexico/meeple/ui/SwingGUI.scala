package hexico.meeple.ui

import scala.swing._
import hexico.meeple.game._
import scala.util.Random
import scala.collection.mutable
import javax.swing.SpringLayout.Constraints

object SwingGUI extends SimpleSwingApplication {
  val random = new Random()
  val tileset = new mutable.Queue ++ Tilesets.START ++ random.shuffle(Tilesets.BASE)
  val board: Board = new Board

  board.addTile(tileset.dequeue(), (0, 0))
  var next = tileset.dequeue()

  def top = new MainFrame {
    title = "Meeple: A Carcassonne Explorer"
    preferredSize = new Dimension(800, 600)

    val boardPanel = new BoardPanel(board)
    val previewPanel = new TilePreviewPanel(50)
    previewPanel.tile = next

    val menu = new GridBagPanel {
      val showMoves = new CheckBox("Show possible moves")
      val c = new Constraints
      c.anchor = GridBagPanel.Anchor.North
      c.gridx = 0
      layout(showMoves) = c
      layout(previewPanel) = c
      c.weighty = 1
      layout(Swing.VGlue) = c
    }
    contents = new BorderPanel {
      layout(new ScrollPane {
        contents = boardPanel
      }) = BorderPanel.Position.Center
      layout(menu) = BorderPanel.Position.East
    }
  }
}
