package hexico.meeple.game

import hexico.meeple.test.UnitSpec
import hexico.meeple.game.{Direction => D}

class DirectionSpec extends UnitSpec {
  "A Direction" should "have modular arithmetic" in {
    assert(D.N + 1 === D.NE)
    assert(D.NE - 2 === D.NW)
    assert(D.S + 4 === D.N )
    assert(D.NW - 3 === D.S)
    assert(D.E + 11 === D.SW)
    assert(D.E - 9 === D.NE)
  }

  it should "have an opposite Direction" in {
    assert(D.N.opposite === D.S)
    assert(D.SW.opposite === D.NE)
  }

  it should "be rotatable as a 4-sided shape" in {
    assert(D.N.rotate(1) === D.E)
    assert(D.E.rotate(3) === D.N)
    assert(D.SE.rotate(-1) === D.NE)
    assert(D.NE.rotate(-2) === D.SW)
  }
}
