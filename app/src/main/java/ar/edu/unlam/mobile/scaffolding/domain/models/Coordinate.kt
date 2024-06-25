package ar.edu.unlam.mobile.scaffolding.domain.models

import android.location.Location
import com.mapbox.geojson.Point

data class Coordinate(
    val latitude: Double,
    val longitude: Double,
    val speed: Float,
) {
    fun toPoint(): Point = Point.fromLngLat(longitude, latitude)
}

fun Location.toCoordinate(): Coordinate = Coordinate(latitude, longitude, speed)
