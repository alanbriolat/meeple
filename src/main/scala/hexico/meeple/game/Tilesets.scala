package hexico.meeple.game

import hexico.meeple.game.{GrassAttachment => G, Direction => D}
import TilesetHelpers._

object Tilesets {
  val START = Vector(
    Tile(Road() at Set(D.N, D.S),
         City() at Set(D.NE, D.E, D.SE),
         Grass at Set(G.W1, G.W2, G.N1, G.S2),
         Grass at Set(G.N2, G.S1) touching Set(1))
  )

  val BASE: Vector[Tile] = Vector.concat(
    // A
    2 of Tile(Road() at D.S,
              Monastery,
              Grass at G.OMNI),
    // B
    4 of Tile(Monastery,
              Grass at G.OMNI),
    // C
    1 of Tile(City(Shield) at D.OMNI),
    // D (excluding start tile)
    3 of Tile(Road() at D.N + D.S,
              City() at D.E_,
              Grass at G.W_ + G.N1 + G.S2,
              Grass at G.N2 + G.S1 touching Set(1)),
    // E
    5 of Tile(City() at D.N_,
              Grass at G.OMNI -- G.N_ touching Set(0)),
    // F
    2 of Tile(City(Shield) at D.W_ ++ D.E_,
              Grass at G.N_ touching Set(0),
              Grass at G.S_ touching Set(0)),
    // G
    1 of Tile(City() at D.N_ ++ D.S_,
              Grass at G.W_ touching Set(0),
              Grass at G.E_ touching Set(0)),
    // H
    3 of Tile(City() at D.W_,
              City() at D.E_,
              Grass at G.N_ ++ G.S_ touching Set(0, 1)),
    // I
    2 of Tile(City() at D.E_,
              City() at D.S_,
              Grass at G.N_ ++ G.W_ touching Set(0, 1)),
    // J
    3 of Tile(Road() at D.E + D.S,
              City() at D.N_,
              Grass at G.W_ + G.E1 + G.S2 touching Set(1),
              Grass at G.E2 + G.S1),
    // K
    3 of Tile(Road() at D.N + D.W,
              City() at D.E_,
              Grass at G.N1 + G.W2,
              Grass at G.S_ + G.N2 + G.W1 touching Set(1)),
    // L
    3 of Tile(Road() at D.N,
              Road() at D.S,
              Road() at D.W,
              City() at D.E_,
              Grass at G.N1 + G.W2,
              Grass at G.N2 + G.S1 touching Set(3),
              Grass at G.S2 + G.W1),
    // M
    2 of Tile(City(Shield) at D.N_ ++ D.W_,
              Grass at G.E_ ++ G.S_ touching Set(0)),
    // N
    3 of Tile(City() at D.N_ ++ D.W_,
              Grass at G.E_ ++ G.S_ touching Set(0)),
    // O
    2 of Tile(Road() at D.E + D.S,
              City(Shield) at D.N_ ++ D.W_,
              Grass at G.E1 + G.S2 touching Set(1),
              Grass at G.E2 + G.S1),
    // P
    3 of Tile(Road() at D.E + D.S,
              City() at D.N_ ++ D.W_,
              Grass at G.E1 + G.S2 touching Set(1),
              Grass at G.E2 + G.S1),
    // Q
    1 of Tile(City(Shield) at D.OMNI - D.S,
              Grass at G.S_ touching Set(0)),
    // R
    3 of Tile(City() at D.OMNI - D.S,
              Grass at G.S_ touching Set(0)),
    // S
    2 of Tile(Road() at D.S,
              City(Shield) at D.OMNI - D.S,
              Grass at G.S1 touching Set(1),
              Grass at G.S2 touching Set(1)),
    // T
    1 of Tile(Road() at D.S,
              City() at D.OMNI - D.S,
              Grass at G.S1 touching Set(1),
              Grass at G.S2 touching Set(1)),
    // U
    8 of Tile(Road() at D.N + D.S,
              Grass at G.W_ + G.N1 + G.S2,
              Grass at G.E_ + G.N2 + G.S1),
    // V
    9 of Tile(Road() at D.S + D.W,
              Grass at G.N_ ++ G.E_ + G.S1 + G.W2,
              Grass at G.S2 + G.W1),
    // W
    4 of Tile(Road() at D.E,
              Road() at D.S,
              Road() at D.W,
              Grass at G.N_ + G.W2 + G.E1,
              Grass at G.E2 + G.S1,
              Grass at G.S2 + G.W1),
    // X
    1 of Tile(Road() at D.N,
              Road() at D.E,
              Road() at D.S,
              Road() at D.W,
              Grass at G.N2 + G.E1,
              Grass at G.E2 + G.S1,
              Grass at G.S2 + G.W1,
              Grass at G.W2 + G.N1)
  )
}
