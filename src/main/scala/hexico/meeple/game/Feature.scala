package hexico.meeple.game

import scala.collection.mutable

abstract class Feature {
  val contains: mutable.MutableList[Feature] = mutable.MutableList()
  def shorthand: Char = getClass.getSimpleName()(0)

  def addFeature(feature: Feature) {
    contains += feature
  }
}

class Road extends Feature
class City extends Feature
class Monastery extends Feature
case object Shield extends Feature

class Grass extends Feature
