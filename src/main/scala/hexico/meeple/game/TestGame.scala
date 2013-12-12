package hexico.meeple.game

import Direction._

object TestGame {
  case class Point[T](x: T, y: T)

  def main(args: Array[String]) {
    val t1 = Tile(
      Feature(City, Set(E)),
      Feature(Road, Set(N, S)),
      Feature(Grass, Set(NW, W, SW)),
      Feature(Grass, Set(NE, SE))
    )
    println(t1)
    println(t1.rotate(1))
    val b = new Board
    b.addTile(t1, (0, 0))
  }
}
