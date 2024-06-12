package ar.edu.unlam.mobile.scaffolding.domain.models.location

import com.mapbox.geojson.Point

data class Coordinate(val latitude: Double, val longitude: Double) {
    fun toPoint(): Point = Point.fromLngLat(longitude, latitude)
}
