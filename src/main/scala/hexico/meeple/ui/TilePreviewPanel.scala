package hexico.meeple.ui

import scala.swing._
import java.awt.{Color, Dimension}
import java.awt.image.BufferedImage
import hexico.meeple.game.{Direction, Tile}
import scala.swing.event.MouseClicked

class TilePreviewPanel (val tileSize: Int = 50) extends Panel {
  val nubSize: Int = tileSize / 5
  val renderer = new TileRenderer(tileSize, tileSize)
  val (width, height) = (tileSize * 2 + 3, tileSize * 2 + 3)
  preferredSize = new Dimension(width, height)
  minimumSize = preferredSize
  maximumSize = preferredSize

  // Location to render each of the 4 preview images
  private val locations = Vector((1, 1), (tileSize + 2, 1),
    (tileSize + 2, tileSize + 2), (1, tileSize + 2))
  // Rendered preview images for each rotation of the tile
  private var rendered: Vector[BufferedImage] = null

  private var _selectedRotation: Option[Int] = None
  def selectedRotation: Option[Int] = _selectedRotation

  listenTo(mouse.clicks)
  reactions += {
    case e: MouseClicked => {
      _selectedRotation = pointToRotation(e.point.x, e.point.y)
      repaint()
    }
  }

  def pointToRotation(x: Int, y: Int): Option[Int] = {
    val interval = tileSize + 1
    if (x % interval == 0 || y % interval == 0) {
      None
    } else {
      ((x - 1) / interval, (y - 1) / interval) match {
        case (0, 0) => Some(0)
        case (1, 0) => Some(1)
        case (1, 1) => Some(2)
        case (0, 1) => Some(3)
        case _ => None
      }
    }
  }

  private var _tile: Tile = null
  def tile: Tile = _tile
  def tile_=(t: Tile) {
    _tile = t
    if (t == null) {
      rendered = null
    } else {
      rendered = (for (i <- 0 to 3) yield renderer.render(t.rotate(i))).toVector
    }
    repaint()
  }

  def previewBox(tileSize: Int, nubSize: Int,
                 corner: Direction.Value, selected: Boolean): BufferedImage = {
    val i = new BufferedImage(tileSize + 2, tileSize + 2, BufferedImage.TYPE_INT_ARGB)
    val g = i.createGraphics()

    g.setBackground(new Color(0, 0, 0, 0))
    g.clearRect(0, 0, tileSize + 2, tileSize + 2)
    g.setColor(Color.BLUE)
    g.drawRect(0, 0, tileSize + 1, tileSize + 1)
    val (x, y) = corner match {
      case Direction.NW => (0, 0)
      case Direction.NE => (tileSize - nubSize + 1, 0)
      case Direction.SE => (tileSize - nubSize + 1, tileSize - nubSize + 1)
      case Direction.SW => (0, tileSize - nubSize + 1)
    }
    if (selected) {
      g.fillRect(x, y, nubSize, nubSize)
    } else {
      g.drawRect(x, y, nubSize, nubSize)
    }

    i
  }

  override def paintComponent(g: Graphics2D) {
    g.clearRect(0, 0, size.width, size.height)
    if (rendered != null) {
      for (i <- 0 to 3) {
        val (x, y) = locations(i)
        g.drawImage(rendered(i), null, x, y)
      }
    }
    // Draw overlay boxes
    for (i <- 0 to 3) {
      val (x, y) = locations(i)
      val selected = selectedRotation == Some(i)
      val box = previewBox(tileSize, nubSize, Direction.NW.rotate(i), selected)
      g.drawImage(box, null, x - 1, y - 1)
    }
  }
}
