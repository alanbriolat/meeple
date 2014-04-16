package hexico.meeple.ui

import hexico.meeple.game.{Tile, BoardChanged, Board}
import scala.swing._
import java.awt.Dimension
import scala.swing.event.{Event, MouseClicked}

case class TileClicked(x: Int, y: Int) extends Event

class BoardPanel (val board: Board, val tileSize: Int) extends Panel {
  val renderer = new TileRenderer(tileSize)

  listenTo(board)
  reactions += {
    case BoardChanged =>
      println("BoardChanged")
      val (minX, maxX, minY, maxY) = board.extent
      val countX = maxX - minX + 1
      val countY = maxY - minY + 1
      preferredSize = new Dimension(countX * tileSize + countX + 1,
                                    countY * tileSize + countY + 1)
      minimumSize = preferredSize
      maximumSize = preferredSize
      revalidate()
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

  var _nextTile: Option[Tile] = None
  def nextTile: Option[Tile] = _nextTile
  def nextTile_=(t: Option[Tile]) {
    _nextTile = t
    repaint()
  }

  def pointToBoard(x: Int, y: Int): Option[(Int, Int)] = {
    if (x % (tileSize + 1) == 0 || y % (tileSize + 1) == 0) {
      None
    } else {
      val (minX, _, minY, _) = board.extent
      Some(((x - 1) / (tileSize + 1) + minX, (y - 1) / (tileSize + 1) + minY))
    }
  }

  override def paintComponent(g: Graphics2D) {
    val (minX, _, minY, _) = board.extent

    g.clearRect(0, 0, size.width, size.height)

    for (((tx, ty), t) <- board.tiles) {
      val (x, y) = (tx - minX, ty - minY)
      g.drawImage(renderer.render(t), null, x * tileSize + x + 1, y * tileSize + y + 1)
    }

    for (t <- _nextTile) {
      for (((tx, ty), rotations) <- board.allValid(t)) {
        val (x, y) = (tx - minX, ty - minY)
        val box = renderer.previewBox(rotations)
        g.drawImage(box, null, x * tileSize + x, y * tileSize + y)
      }
    }
  }
}
