package ar.edu.unlam.mobile.scaffolding.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coordinate(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val routeId: Long,
    val latitude: Double,
    val longitude: Double,
)
