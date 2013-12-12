package hexico.meeple.ui

import scala.swing._
import hexico.meeple.game._
import scala.util.Random

object SwingGUI extends SimpleSwingApplication {
  val tileset = Tileset.base
  val random = new Random()
  val board: Board = new Board
  for (x <- 0 to 6; y <- 0 to 6) {
    board.addTile(tileset.tiles(random.nextInt(tileset.tiles.length)), (x, y))
  }

  class BoardPanel (val board: Board) extends Label {
    val TILE_SIZE: Int = 50

    override def paintComponent(g: Graphics2D) {
      val (minX, maxX, minY, maxY) = board.extent
      preferredSize = new Dimension((maxX - minX + 1) * TILE_SIZE + 1,
                                    (maxY - minY + 1) * TILE_SIZE + 1)

      val renderer = new TileRenderer(TILE_SIZE, TILE_SIZE)

      for (((tx, ty), t) <- board.tiles) {
        g.drawImage(renderer.render(t), null, tx * TILE_SIZE, ty * TILE_SIZE)
      }
    }
  }

  def top = new MainFrame {
    title = "Meeple: A Carcassonne Explorer"
    contents = new ScrollPane {
      preferredSize = new Dimension(640, 480)
      contents = new BoardPanel(board)
    }
  }
}
