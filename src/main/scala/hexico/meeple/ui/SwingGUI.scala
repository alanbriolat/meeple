package hexico.meeple.ui

import scala.swing._
import hexico.meeple.game._
import scala.util.Random
import scala.collection.mutable

object SwingGUI extends SimpleSwingApplication {
  val TILE_SIZE = 50
  val random = new Random()
  val tileset = new mutable.Queue ++ Tilesets.START ++ random.shuffle(Tilesets.BASE)
  val board: Board = new Board
  val boardPanel = new BoardPanel(board, TILE_SIZE)
  val previewPanel = new TilePreviewPanel(TILE_SIZE)
  val stateLabel = new Label(s"${tileset.size - 1} tiles remaining")

  // Add the start tile
  board.addTile(tileset.dequeue(), (0, 0))
  // Put the next tile into the preview panel
  previewPanel.tile = tileset.dequeue()

  // Listen for tile clicks and place tiles
  listenTo(boardPanel)
  reactions += {
    case TileClicked(x, y) => {
      for (tile <- previewPanel.selectedTile if board.valid(tile, (x, y))) {
        board.addTile(tile, (x, y))
        if (tileset.size == 0) {
          stateLabel.text = "Game over!"
          previewPanel.tile = null
        } else {
          stateLabel.text = s"${tileset.size} tiles remaining"
          previewPanel.tile = tileset.dequeue()
        }
      }
    }
  }

  def top = new MainFrame {
    title = "Meeple: A Carcassonne Explorer"
    preferredSize = new Dimension(800, 600)

    val menu = new GridBagPanel {
      val showMoves = new CheckBox("Show possible moves")
      val c = new Constraints
      c.anchor = GridBagPanel.Anchor.North
      c.gridx = 0
      layout(showMoves) = c
      layout(previewPanel) = c
      layout(stateLabel) = c
      c.weighty = 1
      layout(Swing.VGlue) = c
    }
    contents = new BorderPanel {
      layout(new ScrollPane {
        contents = new GridBagPanel {
          val c = new Constraints
          c.anchor = GridBagPanel.Anchor.Center
          layout(boardPanel) = c
        }
      }) = BorderPanel.Position.Center
      layout(menu) = BorderPanel.Position.East
    }
  }
}
