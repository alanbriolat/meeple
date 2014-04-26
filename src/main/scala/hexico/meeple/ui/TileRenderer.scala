package hexico.meeple.ui

import java.awt.image.BufferedImage
import hexico.meeple.game._
import hexico.meeple.game.{Direction => D}
import java.awt.{RenderingHints, BasicStroke, Color}
import scala.swing.Graphics2D


class TileRenderer (val tileSize: Int) {
  val COLOR_GRASS: Color = Color.GREEN
  val COLOR_CITY: Color = Color.GRAY
  val COLOR_SHIELD: Color = Color.DARK_GRAY
  val COLOR_ROAD: Color = Color.BLACK
  val STROKE_ROAD: BasicStroke =
    new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER)
  val COLOR_MONASTERY: Color = Color.RED

  val COLOR_PREVIEW: Color = Color.BLUE

  def directionToPoint(d: D.Value): Point[Double] = d match {
    case D.NW => Point(-1, -1)
    case D.N  => Point(0, -1)
    case D.NE => Point(1, -1)
    case D.E  => Point(1, 0)
    case D.SE => Point(1, 1)
    case D.S  => Point(0, 1)
    case D.SW => Point(-1, 1)
    case D.W  => Point(-1, 0)
  }

  def pointToPixel(p: Point[Double]): Point[Int] = {
    Point((((p.x + 1) / 2) * tileSize).toInt,
          (((p.y + 1) / 2) * tileSize).toInt)
  }

  def drawRoad(g: Graphics2D, points: List[D.Value]) {
    g.setStroke(STROKE_ROAD)
    val vertices = points match {
      case a::Nil =>
        List(directionToPoint(a), directionToPoint(a) * 0.2)
      case a::b::Nil =>
        List(directionToPoint(a), directionToPoint(a) * 0.5,
             directionToPoint(b) * 0.5, directionToPoint(b))
      case _ =>
        println("impossible road")
        List()
    }
    val (xs, ys) = vertices.map(pointToPixel(_).toPair).unzip
    g.drawPolyline(xs.toArray, ys.toArray, xs.length)
  }

  def drawCity(g: Graphics2D, points: List[D.Value]) {
    // Sort points
    val sorted = points.sorted
    // Iterate over pairs of points (starting with (last, first)) and
    // build a list of vertices to draw
    val vertices = (sorted.last :: sorted).sliding(2).flatMap { case List(a, b) =>
      b - a match {
        // Adjacent points, draw directly to the point
        case 1 => List(directionToPoint(b))
        // Adjacent corners, curve away from the edge
        case 2 => List(directionToPoint(a) * 0.5, directionToPoint(b) * 0.5, directionToPoint(b))
        // Opposite corners, curve away from the centre
        case 4 => List(directionToPoint(a + 6) * 0.2, directionToPoint(b))
        // Adjacent corners the "long way round", edge city special case
        case 6 => List(directionToPoint(a - 1) * 0.6, directionToPoint(b))
      }
    }
    val (xs, ys) = vertices.toStream.map(pointToPixel(_).toPair).unzip
    g.fillPolygon(xs.toArray, ys.toArray, xs.length)
  }

  def drawShield(g: Graphics2D, points: List[D.Value]) {
    val (cityXs, cityYs) = points.map(directionToPoint(_).toPair).unzip
    val mean = Point(cityXs.sum / cityXs.length, cityYs.sum / cityYs.length)
    val vertices = List(Point(-0.2, -0.2), Point(-0.1, -0.15), Point(0.0, -0.2),
                        Point(0.1, -0.15), Point(0.2, -0.2), Point(0.2, 0.05),
                        Point(0.0, 0.2), Point(-0.2, 0.05)).map(_ + mean)
    val (xs, ys) = vertices.map(pointToPixel(_).toPair).unzip
    g.fillPolygon(xs.toArray, ys.toArray, xs.length)
  }

  def drawMonastery(g: Graphics2D) {
    val start = pointToPixel(Point(-0.4, -0.4))
    val end = pointToPixel(Point(0.4, 0.4))
    val span = end - start
    g.fillRect(start.x, start.y, span.x, span.y)
  }

  def render(t: Tile): BufferedImage = {
    val i = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB)
    val g = i.createGraphics()

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    g.setBackground(COLOR_GRASS)
    g.clearRect(0, 0, tileSize, tileSize)

    t.features.foreach { f =>
      f.feature match {
        case r: Road =>
          g.setColor(COLOR_ROAD)
          drawRoad(g, f.points.toList)
        case c: City =>
          g.setColor(COLOR_CITY)
          drawCity(g, f.points.toList)
          f.feature.contains.foreach {
            case Shield =>
              g.setColor(COLOR_SHIELD)
              drawShield(g, f.points.toList)
          }
        case Monastery =>
          g.setColor(COLOR_MONASTERY)
          drawMonastery(g)
        case _ => println("unhandled feature")
      }
    }

    // Return BufferedImage
    i
  }

  /**
   * Render an outline box, for previewing moves.
   *
   * @param corners   Set of corners to render nubs on, 0=NW, 1=NE, 2=SE, 3=SW.
   * @param selected  Should the nubs be filled?
   * @return  Transparent image, 2px larger than a tile, with outline and nubs drawn.
   */
  def previewBox(corners: Set[Int], selected: Boolean = false): BufferedImage = {
    val nubSize: Int = tileSize / 5
    val i = new BufferedImage(tileSize + 2, tileSize + 2, BufferedImage.TYPE_INT_ARGB)
    val g = i.createGraphics()

    g.setBackground(new Color(0, 0, 0, 0))
    g.clearRect(0, 0, tileSize + 2, tileSize + 2)
    g.setColor(COLOR_PREVIEW)
    g.drawRect(0, 0, tileSize + 1, tileSize + 1)

    for (corner <- corners) {
      val (x, y) = corner match {
        case 0 => (0, 0)
        case 1 => (tileSize - nubSize + 1, 0)
        case 2 => (tileSize - nubSize + 1, tileSize - nubSize + 1)
        case 3 => (0, tileSize - nubSize + 1)
      }
      if (selected) {
        g.fillRect(x, y, nubSize, nubSize)
      } else {
        g.drawRect(x, y, nubSize, nubSize)
      }
    }

    // Return BufferedImage
    i
  }
}
