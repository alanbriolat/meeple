package hexico.meeple.game

import Direction._

case class Tileset private (tiles: Vector[Tile])

object Tileset {
  def apply(tiles: (Tile, Int)*): Tileset = {
    Tileset(Vector.concat((for ((t, n) <- tiles) yield Vector.fill(n)(t)):_*))
  }

  private implicit def directionToSet(d: Direction): Set[Direction] = Set(d)

  val base: Tileset = Tileset(
    (Tile(Feature(Monastery),
          Feature(Road, S),
          Feature(Grass, OMNI - S)), 2),
    (Tile(Feature(Monastery),
          Feature(Grass, OMNI)), 4),
    (Tile(Feature(City, OMNI, List(Shield))), 1),
    (Tile(Feature(City, E),
          Feature(Road, N + S),
          Feature(Grass, W_),
          Feature(Grass, NE + SE)), 4),
    (Tile(Feature(City, N),
          Feature(Grass, OMNI - N)), 5),
    (Tile(Feature(City, W + E, List(Shield)),
          Feature(Grass, N_),
          Feature(Grass, S_)), 2),
    (Tile(Feature(City, N + S),
          Feature(Grass, W_),
          Feature(Grass, E_)), 1),
    (Tile(Feature(City, W),
          Feature(City, E),
          Feature(Grass, OMNI - W - E)), 3),
    (Tile(Feature(City, E),
          Feature(City, S),
          Feature(Grass, OMNI - E - S)), 2),
    (Tile(Feature(City, N),
          Feature(Road, E + S),
          Feature(Grass, NE ++ W_),
          Feature(Grass, SE)), 3),
    (Tile(Feature(City, E),
          Feature(Road, N + W),
          Feature(Grass, NW),
          Feature(Grass, NE ++ S_)), 3),
    (Tile(Feature(City, E),
          Feature(Road, N),
          Feature(Road, S),
          Feature(Road, W),
          Feature(Grass, NE + SE),
          Feature(Grass, NW),
          Feature(Grass, SW)), 3),
    (Tile(Feature(City, NW + N + W, List(Shield)),
          Feature(Grass, OMNI - NW - N - W)), 2),
    (Tile(Feature(City, NW + N + W),
          Feature(Grass, OMNI - NW - N - W)), 3),
    (Tile(Feature(City, NW + N + W, List(Shield)),
          Feature(Road, E + S),
          Feature(Grass, SE),
          Feature(Grass, NE + SW)), 2),
    (Tile(Feature(City, NW + N + W),
          Feature(Road, E + S),
          Feature(Grass, SE),
          Feature(Grass, NE + SW)), 3),
    (Tile(Feature(City, OMNI -- S_, List(Shield)),
          Feature(Grass, S_)), 1),
    (Tile(Feature(City, OMNI -- S_),
          Feature(Grass, S_)), 3),
    (Tile(Feature(City, OMNI -- S_, List(Shield)),
          Feature(Road, S),
          Feature(Grass, SE),
          Feature(Grass, SW)), 2),
    (Tile(Feature(City, OMNI -- S_),
          Feature(Road, S),
          Feature(Grass, SE),
          Feature(Grass, SW)), 1),
    (Tile(Feature(Road, N + S),
          Feature(Grass, E_),
          Feature(Grass, W_)), 8),
    (Tile(Feature(Road, S + W),
          Feature(Grass, N_ ++ E_),
          Feature(Grass, SW)), 9),
    (Tile(Feature(Road, E),
          Feature(Road, S),
          Feature(Road, W),
          Feature(Grass, N_),
          Feature(Grass, SE),
          Feature(Grass, SW)), 4),
    (Tile(Feature(Road, N),
          Feature(Road, E),
          Feature(Road, S),
          Feature(Road, W),
          Feature(Grass, NW),
          Feature(Grass, NE),
          Feature(Grass, SE),
          Feature(Grass, SW)), 1)
  )

  def main(args: Array[String]) {
    val x: Set[Direction] = W + E
    println(x)
  }
}
