package hexico.meeple.ui

import java.awt.image.BufferedImage
import hexico.meeple.game._
import hexico.meeple.game.Direction._
import java.awt.{BasicStroke, Color}


class TileRenderer (val width: Int, val height: Int) {
  val COLOR_GRASS: Color = Color.GREEN
  val COLOR_CITY: Color = Color.GRAY
  val COLOR_ROAD: Color = Color.BLACK
  val STROKE_ROAD: BasicStroke = new BasicStroke(3)
  val COLOR_MONASTERY: Color = Color.RED

  val (centreX, centreY) = fractionToPixel(0.5, 0.5)

  def directionToPixel(d: Direction, scale: Double = 1.0): (Int, Int) = {
    // Position of the edge relative to the (0.5, 0.5) centre of the tile
    val (edgeX, edgeY) = d match {
      case NW => (-0.5, -0.5)
      case N  => (0.0, -0.5)
      case NE => (0.5, -0.5)
      case E  => (0.5, 0.0)
      case SE => (0.5, 0.5)
      case S  => (0.0, 0.5)
      case SW => (-0.5, 0.5)
      case W  => (-0.5, 0.0)
    }
    // Return pixel position within tile, applying scaling factor to distance from centre
    fractionToPixel(0.5 + edgeX * scale, 0.5 + edgeY * scale)
  }

  def fractionToPixel(f: (Double, Double)): (Int, Int) = ((f._1 * width).toInt, (f._2 * height).toInt)

  def render(t: Tile): BufferedImage = {
    val i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g = i.createGraphics()

    g.setBackground(COLOR_GRASS)
    g.clearRect(0, 0, width, height)

    t.features.foreach { f =>
      f.feature match {
        case r: Road =>
          g.setColor(COLOR_ROAD)
          g.setStroke(STROKE_ROAD)
          f.points.toList match {
            case a::Nil =>
              val (startX, startY) = directionToPixel(a)
              g.drawLine(startX, startY, centreX, centreY)
            case a::b::Nil =>
              val (startX, startY) = directionToPixel(a)
              val (endX, endY) = directionToPixel(b)
              g.drawLine(startX, startY, endX, endY)
            case _ =>
              println("impossible road")
          }
        case c: City =>
          g.setColor(COLOR_CITY)
          // Sort points
          val sorted = f.points.toList.sorted
          // Iterate over pairs of points (starting with (last, first)) and
          // build a list of vertices to draw
          val vertices = (sorted.last :: sorted).sliding(2).flatMap { case List(a, b) =>
            b - a match {
              // Adjacent points, draw directly to the point
              case 1 => List(directionToPixel(b))
              // Adjacent corners, curve away from the edge
              case 2 => List(directionToPixel(a, 0.5), directionToPixel(b, 0.5), directionToPixel(b))
              // Opposite corners, curve away from the centre
              case 4 => List(directionToPixel(a + 6, 0.2), directionToPixel(b))
              // Adjacent corners the "long way round", edge city special case
              case 6 => List(directionToPixel(a - 1, 0.6), directionToPixel(b))
            }
          }
          val (xs, ys) = vertices.toStream.unzip
          g.fillPolygon(xs.toArray, ys.toArray, xs.length)
        case m: Monastery =>
          g.setColor(COLOR_MONASTERY)
          val (startX, startY) = fractionToPixel(0.3, 0.3)
          val (spanX, spanY) = fractionToPixel(0.4, 0.4)
          g.fillRect(startX, startY, spanX, spanY)
        case _ => println("unhandled feature")
      }
    }

    // Return BufferedImage
    i
  }
}
