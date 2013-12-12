package hexico.meeple.ui

import scala.swing._
import java.awt.{BasicStroke, Color}
import hexico.meeple.game._
import hexico.meeple.game.Direction._
import scala.util.Random

object SwingGUI extends SimpleSwingApplication {
  val tileset = Tileset.base
  val random = new Random()
  val board: Board = new Board
  for (x <- 0 to 3; y <- 0 to 3) {
    board.addTile(tileset.tiles(random.nextInt(tileset.tiles.length)), (x, y))
  }

  def directionToPoint(d: Direction): (Float, Float) = d match {
    case NW => (-1f, -1f)
    case N => (0f, -1f)
    case NE => (1f, -1f)
    case E => (1f, 0f)
    case SE => (1f, 1f)
    case S => (0f, 1f)
    case SW => (-1f, 1f)
    case W => (-1f, 0f)
  }

  class BoardPanel (val board: Board) extends Label {
    val TILE_SIZE: Int = 50
    val COLOR_GRASS: Color = Color.GREEN
    val COLOR_CITY: Color = Color.GRAY
    val STROKE_CITY: BasicStroke = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
    val COLOR_ROAD: Color = Color.BLACK
    val STROKE_ROAD: BasicStroke = new BasicStroke(1)
    val COLOR_MONASTERY: Color = Color.RED

    def location(t: (Int, Int), p: (Float, Float)): (Int, Int) = {
      val (tx, ty) = t
      val (px, py) = p
      val scale: Int = TILE_SIZE / 2
      ((tx * TILE_SIZE) + scale + (px * scale).toInt,
       (ty * TILE_SIZE) + scale + (py * scale).toInt)
    }

    override def paintComponent(g: Graphics2D) {
      val (xmin, xmax, ymin, ymax) = board.extent
      preferredSize = new Dimension((xmax - xmin + 1) * TILE_SIZE + 1,
                                    (ymax - ymin + 1) * TILE_SIZE + 1)

      for (((tx, ty), t) <- board.tiles) {
        val loc = location((tx - xmin, ty - ymin), _: (Float, Float))
        val (baseX, baseY) = loc((-1.0f, -1.0f))
        val (centreX, centreY) = loc((0.0f, 0.0f))

        g.setColor(COLOR_GRASS)
        g.fillRect(baseX, baseY, TILE_SIZE, TILE_SIZE)

        for (Feature(kind, points, contains) <- t.features) {
          kind match {
            case City =>
              g.setColor(COLOR_CITY)
              g.setStroke(STROKE_CITY)
              val pxpoints = for {
                d <- points.toVector.sorted
                (relx, rely) = directionToPoint(d)
                (x, y) = loc((relx * 0.8f, rely * 0.8f))
              } yield (x, y)
              val (xs, ys) = pxpoints.unzip
              g.drawPolyline(xs.toArray, ys.toArray, xs.length)
            case Road =>
              g.setColor(COLOR_ROAD)
              g.setStroke(STROKE_ROAD)
              points.toList match {
                case a::Nil =>
                  val (relx, rely) = directionToPoint(a)
                  val (startx, starty) = loc((relx, rely))
                  val (endx, endy) = loc((relx * 0.3f, rely * 0.3f))
                  g.drawLine(startx, starty, endx, endy)
                  g.fillRect(endx - 2, endy - 2, 5, 5)
                case a::b::Nil =>
                  val (startx, starty) = loc(directionToPoint(a))
                  val (endx, endy) = loc(directionToPoint(b))
                  g.drawLine(startx, starty, endx, endy)
                case _ =>
                  println("impossible road")
              }
            case Monastery =>
              g.setColor(COLOR_MONASTERY)
              g.fillRect(centreX - 10, centreY - 10, 21, 21)
            case _ => println("lulz")
          }
        }
        //g.setColor(Color.WHITE)
        //g.drawRect(baseX, baseY, TILE_SIZE, TILE_SIZE)
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
