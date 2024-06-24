package ar.edu.unlam.mobile.scaffolding.domain

object AchievementMultipliers {
    val KILOMETERS = 5
    val CALORIES = 300
    val MINUTES = 30
    val DAYS = 5

    fun getKilometerLevel(kilometers: Double): Pair<Int, Double> {
        var level = 1
        while (kilometers > KILOMETERS * level)
            level++
        return Pair(level, (KILOMETERS * level).toDouble())
    }

    fun getCalorieLevel(calories: Int): Pair<Int, Int> {
        var level = 1
        while (calories > CALORIES * level)
            level++
        return Pair(level, CALORIES * level)
    }

    fun getMinuteLevel(minutes: Int): Pair<Int, Int> {
        var level = 1
        while (minutes > MINUTES * level)
            level++
        return Pair(level, MINUTES * level)
    }

    fun getDayLevel(days: Int): Pair<Int, Int> {
        var level = 1
        while (days > DAYS * level)
            level++
        return Pair(level, DAYS * level)
    }
}
