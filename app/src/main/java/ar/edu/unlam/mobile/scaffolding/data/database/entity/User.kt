package ar.edu.unlam.mobile.scaffolding.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffolding.domain.models.user.User

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val height: Int,
    val weight: Double,
    val kilometers: Double,
    val calories: Int,
    val minutes: Int,
    val days: Int,
) {
    fun toDomain(): User {
        return User(
            id,
            firstName,
            lastName,
            age,
            height,
            weight,
            kilometers,
            calories,
            minutes,
            days,
        )
    }
}
