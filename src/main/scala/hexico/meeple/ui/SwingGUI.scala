package hexico.meeple.ui

import scala.swing._
import hexico.meeple.game._
import scala.util.Random

object SwingGUI extends SimpleSwingApplication {
  val tileset = Tilesets.START ++ Tilesets.BASE
  val random = new Random()
  val board: Board = new Board
  for (x <- -3 to 3; y <- -3 to 3) {
    board.addTile(tileset(random.nextInt(tileset.length)), (x, y))
  }

  class BoardPanel (val board: Board) extends Label {
    val TILE_SIZE: Int = 50

    override def paintComponent(g: Graphics2D) {
      val (minX, maxX, minY, maxY) = board.extent
      val countX = maxX - minX + 1
      val countY = maxY - minY + 1
      preferredSize = new Dimension(countX * TILE_SIZE + countX + 1,
                                    countY * TILE_SIZE + countY + 1)

      val renderer = new TileRenderer(TILE_SIZE, TILE_SIZE)

      for (((tx, ty), t) <- board.tiles) {
        val (x, y) = (tx - minX, ty - minY)
        g.drawImage(renderer.render(t), null, x * TILE_SIZE + x + 1, y * TILE_SIZE + y + 1)
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
