package hexico.meeple.game

import hexico.meeple.game.{Direction => D, GrassAttachment => G}

sealed abstract class TilePatch {
  def rotate(n: Int): TilePatch
}

case class TileFeature(feature: Feature, points: Set[D.Value] = Set()) extends TilePatch {
  def rotate(n: Int): TileFeature = TileFeature(feature, points.map(_.rotate(n)))

  def at(newPoints: Set[D.Value]): TileFeature = TileFeature(feature, newPoints)
}

case class TileGrass(points: Set[G.Value] = Set(), touches: Set[Int] = Set()) extends TilePatch {
  def rotate(n: Int): TileGrass = TileGrass(points.map(_.rotate(n)))

  def at(newPoints: Set[G.Value]): TileGrass = this.copy(points=newPoints)

  def touching(newTouches: Set[Int]): TileGrass = this.copy(touches=newTouches)
}

case class Tile(patches: Vector[TilePatch]) {
  val features: Seq[TileFeature] = patches.collect({
    case tf: TileFeature => tf
  })

  val featureEdges: Map[D.Value, TileFeature] = patches.collect({
    case tf: TileFeature => tf.points.map(_ -> tf)
  }).flatten.toMap

  val grassEdges: Map[G.Value, TileGrass] = patches.collect({
    case tg: TileGrass => tg.points.map(_ -> tg)
  }).flatten.toMap

  def rotate(n: Int): Tile = Tile(patches.map(_.rotate(n)))
}

object Tile {
  def apply(patches: TilePatch*): Tile = Tile(patches.toVector)
}
