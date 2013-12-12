package hexico.meeple.game

import Direction._

case class Tile (features: Feature*) {
  def rotate(n: Int): Tile = {
    new Tile((for (f <- features) yield f.rotate(n)):_*)
  }

  override def toString = {
    val points: Map[Direction, Char] = (for {
      f <- features
      d <- f.points
    } yield d -> f.kind.shorthand).toMap

    def p(d: Direction): Char = if (points contains d) points(d) else ' '

    s"${p(NW)} ${p(N)} ${p(NE)}\n${p(W)}   ${p(E)}\n${p(SW)} ${p(S)} ${p(SE)}"
  }
}
