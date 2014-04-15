package hexico.meeple.game

import hexico.meeple.game.{Direction => D, GrassAttachment => G}

sealed abstract class TilePatch {
  def rotate(n: Int): TilePatch
}

case class TileFeature(feature: Feature, points: Set[D.Value] = Set()) extends TilePatch {
  def rotate(n: Int): TileFeature =
    TileFeature(feature, for (p <- points) yield p.rotate(n))

  def at(newPoints: Set[D.Value]): TileFeature = TileFeature(feature, newPoints)
}

case class TileGrass(points: Set[G.Value] = Set(), touches: Set[Int] = Set()) extends TilePatch {
  def rotate(n: Int): TileGrass =
    TileGrass(for (p <- points) yield p.rotate(n), touches)

  def at(newPoints: Set[G.Value]): TileGrass = this.copy(points=newPoints)

  def touching(newTouches: Set[Int]): TileGrass = this.copy(touches=newTouches)
}

case class Tile(patches: Vector[TilePatch]) {
  val featureEdges: Map[D.Value, TileFeature] = patches.collect({
    case tf: TileFeature => for (edge <- tf.points) yield edge -> tf
  }).flatten.toMap

  val grassEdges: Map[G.Value, TileGrass] = patches.collect({
    case tg: TileGrass => for (edge <- tg.points) yield edge -> tg
  }).flatten.toMap

  def rotate(n: Int): Tile = {
    Tile(for (p <- patches) yield p.rotate(n))
  }

  def features: Seq[TileFeature] = patches flatMap {
    case f: TileFeature => Some(f)
    case _ => None
  }
}

object Tile {
  def apply(patches: TilePatch*): Tile = Tile(patches.toVector)
}
