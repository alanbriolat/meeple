package hexico.meeple.ui

import Numeric.Implicits._

case class Point[T : Numeric](x: T, y: T) {
  def +(other: Point[T]): Point[T] = Point[T](x + other.x, y + other.y)
  def +(other: T): Point[T] = Point[T](x + other, y + other)

  def -(other: Point[T]): Point[T] = Point[T](x - other.x, y - other.y)
  def -(other: T): Point[T] = Point[T](x - other, y - other)

  def *(other: Point[T]): Point[T] = Point[T](x * other.x, y * other.y)
  def *(other: T): Point[T] = Point[T](x * other, y * other)

  def toPair: (T, T) = (x, y)
}
