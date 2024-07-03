package ar.edu.unlam.mobile.scaffolding.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import java.util.Date

@Entity
data class Route(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val distance: Double,
    val durationSeconds: Long,
    val calories: Long,
    val maxSpeed: Double,
    val avgSpeed: Double,
    val coordinates: String,
    val date: Date,
) {
    fun toDomain(): Route? {
        this?.let {
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
        return null
    }
}
