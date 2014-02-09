package hexico.meeple.game

import hexico.meeple.Util

/**
 * Mixin to add modular arithmetic to an Enumeration.
 *
 * Creates an implicit class within an enumeration which adds integer addition
 * and subtraction for getting enum values relative to a particular value.
 * Values are kept within the range of the enumeration, wrapping around in both
 * directions.
 */
trait ModularEnumeration { self: Enumeration =>
  implicit class ModularArithmetic (v: Value) {
    def +(i: Int): Value = apply(Util.positiveModulo(v.id + i, maxId))
    def -(i: Int): Value = apply(Util.positiveModulo(v.id - i, maxId))
  }
}

/**
 * Add an automatic conversion to a singleton set to an Enumeration.
 */
trait AutoSingleton {self: Enumeration =>
  implicit def toSingleton(v: Value): Set[Value] = Set(v)
}

/**
 * A representation of the points on a tile which features can touch and
 * can attach to adjacent features via.  Has an .opposite method to aid finding
 * attachment points on adjacent tiles.
 */
object Direction extends Enumeration with ModularEnumeration with AutoSingleton {
  type Direction = Value

  val NW, N, NE, E, SE, S, SW, W = Value
  val OMNI = values
  val N_ = Set(NW, N, NE)
  val E_ = Set(NE, E, SE)
  val S_ = Set(SE, S, SW)
  val W_ = Set(SW, W, NW)

  implicit class RichDirection (v: Value) {
    def opposite: Value = v + 4
    def rotate(n: Int): Value = v + (n * 2)
  }
}

/**
 * A representation of the points on a tile across which a grass feature can
 * connect to adjacent grass features.  Implemented with modular arithmetic,
 * but also with an .opposite method to aid with finding attachment points on
 * adjacent tiles.
 *
 * Attachment points are clockwise from the top-left.
 *
 * {{{
 * scala> val a = GrassAttachment.N1
 * a: hexico.meeple.game.GrassAttachment.Value = N1
 * scala> a.opposite
 * res0: hexico.meeple.game.GrassAttachment.Value = S2
 * }}}
 */
object GrassAttachment extends Enumeration with ModularEnumeration with AutoSingleton {
  type GrassAttachment = Value

  val N1, N2, E1, E2, S1, S2, W1, W2 = Value
  val OMNI = values
  val N_ = Set(N1, N2)
  val E_ = Set(E1, E2)
  val S_ = Set(S1, S2)
  val W_ = Set(W1, W2)

  implicit class RichGrassAttachment (v: Value) {
    def opposite: Value = if (v.id % 2 == 0) v + 5 else v + 3
    def rotate(n: Int): Value = v + (n * 2)
  }
}
