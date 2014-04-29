package hexico.meeple.ui

import hexico.meeple.test.UnitSpec

class PointSpec extends UnitSpec {
  "A Point[Int]" should "have structural equality" in {
    assert(Point(1, 2) == Point(1, 2))
  }

  it should "implement basic arithmetic with scalars" in {
    assert(Point(10, 22) + 8 == Point(18, 30))
    assert(Point(5, 8) - 3 == Point(2, 5))
    assert(Point(10, 10) * 3 == Point(30, 30))
  }

  it should "implement basic arithmetic with other points" in {
    assert(Point(15, 10) + Point(-3, 8) == Point(12, 18))
    assert(Point(-19, -3) - Point(3, 5) == Point(-22, -8))
    assert(Point(-5, 8) * Point(4, 5) == Point(-20, 40))
  }

  "A Point[Double]" should "have structural equality" in {
    assert(Point(1.0, 2.5) == Point(1.0, 2.5))
  }

  it should "implement basic arithmetic with scalars" in {
    assert(Point(10.5, 22.0) + 8 == Point(18.5, 30.0))
    assert(Point(5.0, 8.5) - 3 == Point(2.0, 5.5))
    assert(Point(10.5, 10.5) * 3 == Point(31.5, 31.5))
  }

  it should "implement basic arithmetic with other points" in {
    assert(Point(15.5, 10.5) + Point(-3.5, 8.5) == Point(12.0, 19.0))
    assert(Point(-19.5, -3.5) - Point(3.5, 5.5) == Point(-23.0, -9.0))
    assert(Point(-5.0, 8.0) * Point(4.0, 5.0) == Point(-20.0, 40.0))
  }
}
