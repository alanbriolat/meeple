package hexico.meeple.game

import Direction._
import GrassAttachment._
import scala.collection.mutable

case class TileFeature(feature: Feature, points: Set[Direction] = Set()) {
  def rotate(n: Int): TileFeature =
    TileFeature(feature, for (p <- points) yield p.rotate(n))
}

case class TileGrass(points: Set[GrassAttachment], features: Set[Feature] = Set()) {
  def rotate(n: Int): TileGrass =
    TileGrass(for (p <- points) yield p.rotate(n), features)
}

class Tile {
  val features: mutable.MutableList[TileFeature] = mutable.MutableList()
  val grass: mutable.MutableList[TileGrass] = mutable.MutableList()

  private def this(features: TraversableOnce[TileFeature], grass: TraversableOnce[TileGrass]) {
    this()
    this.features ++= features
    this.grass ++= grass
  }

  def addFeature(feature: Feature, attach: Set[Direction] = Set()): Feature = {
    // TODO: check that *attach* doesn't overlap an existing feature
    features += TileFeature(feature, attach)
    feature
  }

  def addGrass(attach: Set[GrassAttachment], touches: Set[Feature] = Set()) {
    // TODO: check that *attach* doesn't overlap an existing grass
    // TODO: check that all features in *touches* exist in this tile
    grass += TileGrass(attach, touches)
  }

  def rotate(n: Int): Tile =
    new Tile(for (f <- features) yield f.rotate(n), for (g <- grass) yield g.rotate(n))

  override def toString = {
    val points: Map[Direction, Char] = (for {
      TileFeature(f, points) <- features
      d <- points
    } yield d -> f.shorthand).toMap

    def p(d: Direction): Char = if (points contains d) points(d) else ' '

    s"${p(NW)} ${p(N)} ${p(NE)}\n${p(W)}   ${p(E)}\n${p(SW)} ${p(S)} ${p(SE)}"
  }
}
