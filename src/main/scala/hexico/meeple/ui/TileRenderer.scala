package hexico.meeple.ui

import java.awt.image.BufferedImage
import hexico.meeple.game._
import hexico.meeple.game.Direction._
import java.awt.{BasicStroke, Color}


class TileRenderer (val width: Int, val height: Int) {
  val COLOR_GRASS: Color = Color.GREEN
  val COLOR_CITY: Color = Color.GRAY
  val STROKE_CITY: BasicStroke = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
  val COLOR_ROAD: Color = Color.BLACK
  val STROKE_ROAD: BasicStroke = new BasicStroke(3)
  val COLOR_MONASTERY: Color = Color.RED

  def directionToPoint(d: Direction, scale: Double = 1.0): (Int, Int) = {
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

    for (Feature(kind, points, contains) <- t.features) {
      kind match {
        case Road =>
          g.setColor(COLOR_ROAD)
          g.setStroke(STROKE_ROAD)
          points.toList match {
            case a::Nil =>
              val (startX, startY) = directionToPoint(a)
              val (endX, endY) = directionToPoint(a, 0.3)
              g.drawLine(startX, startY, endX, endY)
              g.fillRect(endX - 2, endY - 2, 5, 5)
            case a::b::Nil =>
              val (startX, startY) = directionToPoint(a)
              val (endX, endY) = directionToPoint(b)
              g.drawLine(startX, startY, endX, endY)
            case _ =>
              println("impossible road")
          }
        case Monastery =>
          g.setColor(COLOR_MONASTERY)
          val (startX, startY) = fractionToPixel(0.3, 0.3)
          val (spanX, spanY) = fractionToPixel(0.4, 0.4)
          g.fillRect(startX, startY, spanX, spanY)
        case _ =>
      }
    }

    // Return BufferedImage
    i
  }
}
