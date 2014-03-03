package hexico.meeple.game

object TilesetHelpers {
  def Grass: TileGrass = TileGrass()

  implicit def toTileFeature(f: Feature): TileFeature = TileFeature(f)

  implicit class RichInt(i: Int) {
    def of(t: Tile): Vector[Tile] = Vector.fill(i)(t)
  }

  implicit def autoSingleton[T <: Enumeration](v: T#Value): Set[T#Value] = Set(v)
}
