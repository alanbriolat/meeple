package hexico.meeple.ui

import hexico.meeple.game.{BoardChanged, Board}
import scala.swing._
import java.awt.Dimension
import scala.swing.event.{Event, MouseClicked}

case class TileClicked(x: Int, y: Int) extends Event

class BoardPanel (val board: Board) extends Panel {
  val TILE_SIZE: Int = 50
  val renderer = new TileRenderer(TILE_SIZE, TILE_SIZE)

  listenTo(board)
  reactions += {
    case BoardChanged =>
      println("BoardChanged")
      repaint()
  }
  listenTo(mouse.clicks)
  reactions += {
    case e: MouseClicked => {
      println("MouseClicked")
      for ((x, y) <- pointToBoard(e.point.x, e.point.y)) {
        publish(TileClicked(x, y))
      }
    }
  }

  def pointToBoard(x: Int, y: Int): Option[(Int, Int)] = {
    if (x % (TILE_SIZE + 1) == 0 || y % (TILE_SIZE + 1) == 0) {
      None
    } else {
      val (minX, _, minY, _) = board.extent
      Some(((x - 1) / (TILE_SIZE + 1) + minX, (y - 1) / (TILE_SIZE + 1) + minY))
    }
  }

  override def paintComponent(g: Graphics2D) {
    val (minX, maxX, minY, maxY) = board.extent
    val countX = maxX - minX + 1
    val countY = maxY - minY + 1
    preferredSize = new Dimension(countX * TILE_SIZE + countX + 1,
      countY * TILE_SIZE + countY + 1)

    g.clearRect(0, 0, size.width, size.height)

    for (((tx, ty), t) <- board.tiles) {
      val (x, y) = (tx - minX, ty - minY)
      g.drawImage(renderer.render(t), null, x * TILE_SIZE + x + 1, y * TILE_SIZE + y + 1)
    }
  }
}
