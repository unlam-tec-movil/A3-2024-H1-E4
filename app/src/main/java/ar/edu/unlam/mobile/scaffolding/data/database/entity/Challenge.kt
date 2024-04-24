package ar.edu.unlam.mobile.scaffolding.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Challenge(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
    val points: Double,
    val amount: Double,
    val complete: Boolean
)
