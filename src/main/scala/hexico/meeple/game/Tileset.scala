package hexico.meeple.game

import hexico.meeple.game.{GrassAttachment => G, Direction => D}
import scala.collection.mutable

class Tileset {
  val tiles: mutable.MutableList[Tile] = mutable.MutableList()

  def addTiles(t: Tile, copies: Int) {
    tiles ++= List.fill(copies)(t)
  }
}

object Tileset {
  implicit def autoSingleton[T <: Enumeration](v: T#Value): Set[T#Value] = Set(v)

  val BASE: Tileset = new Tileset {
    // A
    addTiles(new Tile {
      addFeature(new Road, D.S)
      addFeature(new Monastery)
      addGrass(G.OMNI)
    }, 2)
    // B
    addTiles(new Tile {
      addFeature(new Monastery)
      addGrass(G.OMNI)
    }, 4)
    // C
    addTiles(new Tile {
      addFeature(new City {
        addFeature(Shield)
      }, D.OMNI)
    }, 1)
    // D (inc. start tile)
    addTiles(new Tile {
      addFeature(new Road, D.N + D.S)
      val c1 = addFeature(new City, D.E_)
      addGrass(G.W_ + G.N1 + G.S2)
      addGrass(G.N2 + G.S1, Set(c1))
    }, 4)
    // E
    addTiles(new Tile {
      val c1 = addFeature(new City, D.N_)
      addGrass(G.OMNI -- G.N_, Set(c1))
    }, 5)
    // F
    addTiles(new Tile {
      val c1 = addFeature(new City {
        addFeature(Shield)
      }, D.W_ ++ D.E_)
      addGrass(G.N_, Set(c1))
      addGrass(G.S_, Set(c1))
    }, 2)
    // G
    addTiles(new Tile {
      val c1 = addFeature(new City, D.N_ ++ D.S_)
      addGrass(G.W_, Set(c1))
      addGrass(G.E_, Set(c1))
    }, 1)
    // H
    addTiles(new Tile {
      val c1 = addFeature(new City, D.W_)
      val c2 = addFeature(new City, D.E_)
      addGrass(G.N_ ++ G.S_, Set(c1, c2))
    }, 3)
    // I
    addTiles(new Tile {
      val c1 = addFeature(new City, D.E_)
      val c2 = addFeature(new City, D.S_)
      addGrass(G.N_ ++ G.W_, Set(c1, c2))
    }, 2)
    // J
    addTiles(new Tile {
      addFeature(new Road, D.E + D.S)
      val c1 = addFeature(new City, D.N_)
      addGrass(G.W_ + G.E1 + G.S2, Set(c1))
      addGrass(G.E2 + G.S1)
    }, 3)
    // K
    addTiles(new Tile {
      addFeature(new Road, D.N + D.W)
      val c1 = addFeature(new City, D.E_)
      addGrass(G.N1 + G.W2)
      addGrass(G.N2 ++ G.S_ + G.W1, Set(c1))
    }, 3)
    // L
    addTiles(new Tile {
      addFeature(new Road, D.N)
      addFeature(new Road, D.S)
      addFeature(new Road, D.W)
      val c1 = addFeature(new City, D.E_)
      addGrass(G.N1 + G.W2)
      addGrass(G.N2 + G.S1, Set(c1))
      addGrass(G.S2 + G.W1)
    }, 3)
    // M
    addTiles(new Tile {
      val c1 = addFeature(new City {
        addFeature(Shield)
      }, D.N_ ++ D.W_)
      addGrass(G.E_ ++ G.S_, Set(c1))
    }, 2)
    // N
    addTiles(new Tile {
      val c1 = addFeature(new City, D.N_ ++ D.W_)
      addGrass(G.E_ ++ G.S_, Set(c1))
    }, 3)
    // O
    addTiles(new Tile {
      addFeature(new Road, D.E + D.S)
      val c1 = addFeature(new City {
        addFeature(Shield)
      }, D.N_ ++ D.W_)
      addGrass(G.E1 + G.S2, Set(c1))
      addGrass(G.E2 + G.S1)
    }, 2)
    // P
    addTiles(new Tile {
      addFeature(new Road, D.E + D.S)
      val c1 = addFeature(new City, D.N_ ++ D.W_)
      addGrass(G.E1 + G.S2, Set(c1))
      addGrass(G.E2 + G.S1)
    }, 3)
    // Q
    addTiles(new Tile {
      val c1 = addFeature(new City {
        addFeature(Shield)
      }, D.OMNI - D.S)
      addGrass(G.S_, Set(c1))
    }, 1)
    // R
    addTiles(new Tile {
      val c1 = addFeature(new City, D.OMNI - D.S)
      addGrass(G.S_, Set(c1))
    }, 3)
    // S
    addTiles(new Tile {
      addFeature(new Road, D.S)
      val c1 = addFeature(new City {
        addFeature(Shield)
      }, D.OMNI - D.S)
      addGrass(G.S1, Set(c1))
      addGrass(G.S2, Set(c1))
    }, 2)
    // T
    addTiles(new Tile {
      addFeature(new Road, D.S)
      val c1 = addFeature(new City, D.OMNI - D.S)
      addGrass(G.S1, Set(c1))
      addGrass(G.S2, Set(c1))
    }, 1)
    // U
    addTiles(new Tile {
      addFeature(new Road, D.N + D.S)
      addGrass(G.N1 + G.S2 ++ G.W_)
      addGrass(G.N2 + G.S1 ++ G.E_)
    }, 8)
    // V
    addTiles(new Tile {
      addFeature(new Road, D.S + D.W)
      addGrass(G.N_ ++ G.E_ + G.S1 + G.W2)
      addGrass(G.S2 + G.W1)
    }, 9)
    // W
    addTiles(new Tile {
      addFeature(new Road, D.E)
      addFeature(new Road, D.S)
      addFeature(new Road, D.W)
      addGrass(G.W2 ++ G.N_ + G.E1)
      addGrass(G.E2 + G.S1)
      addGrass(G.S2 + G.W1)
    }, 4)
    // X
    addTiles(new Tile {
      addFeature(new Road, D.N)
      addFeature(new Road, D.E)
      addFeature(new Road, D.S)
      addFeature(new Road, D.W)
      addGrass(G.N2 + G.E1)
      addGrass(G.E2 + G.S1)
      addGrass(G.S2 + G.W1)
      addGrass(G.W2 + G.N1)
    }, 1)
  }
}
