package ar.edu.unlam.mobile.scaffolding.domain.models

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val picture: ByteArray?,
    val age: Int,
    val height: Int,
    val weight: Double,
    val kilometers: Double,
    val calories: Int,
    val minutes: Int,
    val days: Int,
) {
    fun toEntity(): ar.edu.unlam.mobile.scaffolding.data.database.entity.User {
        return ar.edu.unlam.mobile.scaffolding.data.database.entity.User(
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
