package hexico.meeple.game

import scala.collection.mutable
import scala.swing.Publisher
import scala.swing.event.Event
import hexico.meeple.game.{Direction => D}

/**
 * We need to accurately represent tile features in a way that is both accurate for an individual tile and also allows
 * for traversing a feature that spans many tiles.  Tiles are connected by their edges/corners, so there needs to be a
 * representation of both the distinct features present on the tile, and an association between edges/corners and the
 * features for feature traversal.
 *
 * There are 2 main kinds of feature traversal.  (1) Edge-directed traversal.  For features like roads and cities, you
 * take a look at the edges it touches, find the adjacent tiles, find the corresponding same-type features on the
 * corresponding edges, find the edges that touches, and so on.  (2) Edge-constrained traversal.  For features like
 * fields, the presence of a feature on an edge interrupts the field.  Roads often split a field into multiple fields,
 * cities stretch to the corners and prevent a field from propagating over that edge.  An edge can have more than one
 * field, but a corner can only ever have a single field.  From a field feature, we take each corner it touches,
 * find tiles that make up the same corner (4-adjacent, not 8-adjacent), and propagate to the grass features on the
 * corresponding corners only if the edge feature between the tiles is non-blocking.  For example, sharing a corner
 * and joined by open field or road is fine, but joined by a city isn't.
 */

case object BoardChanged extends Event

class Board extends Publisher {
  private val _tiles: mutable.Map[(Int, Int), Tile] = mutable.Map().withDefaultValue(null)

  def tiles: Map[(Int, Int), Tile] = _tiles.toMap

  def addTile(t: Tile, p: (Int, Int)) {
    _tiles(p) = t
    publish(BoardChanged)
  }

  /**
   * Get the extent of the board.  This includes all places that could possibly
   * be played next, so is 1 tile larger in every direction than the extent of
   * the played tiles.
   *
   * @return (minX, maxX, minY, maxY)
   */
  def extent: (Int, Int, Int, Int) = {
    val (xs, ys) = _tiles.keys.unzip
    (xs.min - 1, xs.max + 1, ys.min - 1, ys.max + 1)
  }

  def adjacentIndex(p: (Int, Int), d: D.Value): (Int, Int) = {
    val (x, y) = p
    d match {
      case D.NW => (x - 1, y - 1)
      case D.N  => (x, y - 1)
      case D.NE => (x + 1, y - 1)
      case D.E  => (x + 1, y)
      case D.SE => (x + 1, y + 1)
      case D.S  => (x, y + 1)
      case D.SW => (x - 1, y + 1)
      case D.W  => (x - 1, y)
    }
  }

  def adjacent(p: (Int, Int), d: D.Value): Option[Tile] = _tiles.get(adjacentIndex(p, d))

  def adjacent4(p: (Int, Int)): Vector[(D.Value, Tile)] = {
    (for (i <- 0 to 3; d = D.N.rotate(i); t <- adjacent(p, d)) yield (d, t)).toVector
  }

  def adjacent8(p: (Int, Int)): Vector[(D.Value, Tile)] = {
    (for (i <- 0 to 7; d = D.NW + i; t <- adjacent(p, d)) yield (d, t)).toVector
  }

  def valid(t: Tile, p: (Int, Int)): Boolean = {
    !(_tiles contains p) && checkAdjacent(t, p)
  }

  def allValid(t: Tile): Map[(Int, Int), Set[Int]] = {
    val (minX, maxX, minY, maxY) = extent
    (for (x <- minX to maxX; y <- minY to maxY) yield {
      val p = (x, y)
      val rotations = (0 to 3).filter(i => valid(t.rotate(i), p)).toSet
      if (rotations.size > 0) Some(p -> rotations) else None
    }).flatten.toMap
  }

  def checkAdjacent(t1: Tile, p: (Int, Int)): Boolean = {
    val tiles = adjacent4(p)
    tiles.length > 0 && (for ((d, t2) <- tiles) yield check(t1, t2, d)).forall(identity)
  }

  def check(t1: Tile, t2: Tile, d: D.Value): Boolean = {
    (t1.featureEdges.get(d), t2.featureEdges.get(d.opposite)) match {
      case (None, None) => true
      case (Some(f1), Some(f2)) if f1.feature.getClass == f2.feature.getClass => true
      case _ => false
    }
  }
}
