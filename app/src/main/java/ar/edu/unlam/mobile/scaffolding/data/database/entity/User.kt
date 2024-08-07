package ar.edu.unlam.mobile.scaffolding.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.edu.unlam.mobile.scaffolding.domain.models.User

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstName: String,
    val lastName: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val picture: ByteArray?,
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
            picture,
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
