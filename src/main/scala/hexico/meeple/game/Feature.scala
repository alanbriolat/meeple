package hexico.meeple.game

import hexico.meeple.game.Direction._

sealed abstract class FeatureKind (val occupiable: Boolean) {
  def shorthand: Char = getClass.getSimpleName()(0)
}
case object Road extends FeatureKind(true)
case object City extends FeatureKind(true)
case object Grass extends FeatureKind(true)
case object Monastery extends FeatureKind(true)
case object Shield extends FeatureKind(false)

case class Feature(kind: FeatureKind,
                   points: Set[Direction] = Set(),
                   contains: Seq[FeatureKind] = Seq()) {
  def shorthand: Char = kind.shorthand

  def rotate(n: Int): Feature = {
    this.copy(points = for (p <- points) yield Direction.rotate(p, n * 2))
  }
}
