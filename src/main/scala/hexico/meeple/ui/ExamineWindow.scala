package hexico.meeple.ui

import scala.swing._
import hexico.meeple.game._

class ExamineWindow (tiles: Seq[Tile]) extends Frame {
  val TILE_SIZE = 50

  val board = new Board
  val boardPanel = new BoardPanel(board, TILE_SIZE)
  for ((t, i) <- tiles.zipWithIndex) {
    for (r <- 0 to 3) {
      board.addTile(t.rotate(r), (r, i))
    }
  }
  println(board.extent)

  title = "Meeple: Examine tileset"
  preferredSize = new Dimension(800, 600)

  contents = new ScrollPane {
    contents = new GridBagPanel {
      val c = new Constraints
      c.anchor = GridBagPanel.Anchor.North
      layout(boardPanel) = c
    }
  }
}
