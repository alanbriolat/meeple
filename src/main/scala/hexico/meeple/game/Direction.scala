package hexico.meeple.game

import hexico.meeple.Util

object Direction extends Enumeration {
  type Direction = Value
  val NW, N, NE, E, SE, S, SW, W = Value
  val OMNI = values
  val N_ = Set(NW, N, NE)
  val E_ = Set(NE, E, SE)
  val S_ = Set(SE, S, SW)
  val W_ = Set(SW, W, NW)

  def rotate(d: Direction, n: Int = 1): Direction = {
    Direction(Util.positiveModulo(d.id + n, maxId))
  }
}
