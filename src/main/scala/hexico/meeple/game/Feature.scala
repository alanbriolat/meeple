package hexico.meeple.game

import hexico.meeple.game.Direction.Direction
import hexico.meeple.game.Features2.SubFeature

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
    this.copy(points = for (p <- points) yield p + n * 2)
  }
}


object Features2 {
  import hexico.meeple.game.Attachment._
  import hexico.meeple.game.GrassAttachment._

  sealed abstract class Feature

  trait Attached {
    val points: Seq[Attachment]
  }

  case class Grass (points: Seq[Attachment],
                    grassPoints: Seq[GrassAttachment],
                    contents: Seq[SubFeature]) extends Feature with Attached
  case class Road (points: Seq[Attachment]) extends Feature with Attached
  case class City (points: Seq[Attachment]) extends Feature with Attached
  case object Monastery extends Feature


  sealed abstract class SubFeature
  case object Shield extends SubFeature
}

object Features3 {
  import hexico.meeple.game.Attachment._
  import hexico.meeple.game.GrassAttachment._

  trait Attached { self: Feature =>
    val points: Seq[Attachment]
  }

  abstract class Feature (val contents: Seq[SubFeature] = Seq()) {
    def copy: Feature
  }

  class Grass (val points: Seq[Attachment],
               val grassPoints: Seq[GrassAttachment],
               contents: Seq[SubFeature] = Seq()) extends Feature(contents) with Attached {
    def copy: Grass = new Grass(points, grassPoints, contents)
  }

  class Road (val points: Seq[Attachment]) extends Feature with Attached {
    def copy: Road = new Road(points)
  }

  class Monastery extends Feature

  object FeatureConversions {
    implicit def stringToAttachments(s: String): Seq[Attachment] = {
      for (c: Char <- s) yield Attachment(c.toInt)
    }
  }
}
