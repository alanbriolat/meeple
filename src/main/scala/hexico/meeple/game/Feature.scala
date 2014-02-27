package hexico.meeple.game

abstract class Feature(val contains: Feature*) {
  def shorthand: Char = getClass.getSimpleName()(0)
}

case class Road(override val contains: Feature*) extends Feature
case class City(override val contains: Feature*) extends Feature
case object Monastery extends Feature
case object Shield extends Feature
