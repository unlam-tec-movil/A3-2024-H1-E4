package ar.edu.unlam.mobile.scaffolding.domain.models

import ar.edu.unlam.mobile.scaffolding.data.database.entity.Route
import java.util.Date

data class Route(
    val id: Long = 0,
    val userId: Long,
    val distance: Double,
    val durationSeconds: Long,
    val calories: Long,
    val maxSpeed: Double,
    val avgSpeed: Double,
    val coordinates: String,
    val date: Date,
) {
    fun toEntity(): Route {
        return Route(
            id,
            userId,
            distance,
            durationSeconds,
            calories,
            maxSpeed,
            avgSpeed,
            coordinates,
            date,
        )
    }
}
