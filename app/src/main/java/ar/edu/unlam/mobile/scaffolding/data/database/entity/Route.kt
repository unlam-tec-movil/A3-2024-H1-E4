package ar.edu.unlam.mobile.scaffolding.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Route(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val distance: Double,
    val durationSeconds: Long
)