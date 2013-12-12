package hexico.meeple.game

import scala.collection.mutable

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
class Board {
  private val _tiles: mutable.Map[(Int, Int), Tile] = mutable.Map().withDefaultValue(null)

  def tiles: Map[(Int, Int), Tile] = _tiles.toMap

  def addTile(t: Tile, p: (Int, Int)) {
    _tiles(p) = t
  }

  def extent: (Int, Int, Int, Int) = {
    val (xs, ys) = _tiles.keys.unzip
    (xs.min, xs.max, ys.min, ys.max)
  }
}
