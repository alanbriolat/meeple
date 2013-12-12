package hexico.meeple

object Util {
  def positiveModulo(x: Int, y: Int): Int = {
    val r = x % y
    if (r < 0) r + y else r
  }
}
