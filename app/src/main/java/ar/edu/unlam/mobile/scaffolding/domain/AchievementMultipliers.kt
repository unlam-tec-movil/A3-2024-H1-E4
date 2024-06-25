package ar.edu.unlam.mobile.scaffolding.domain

object AchievementMultipliers {
    private const val KILOMETERS = 5.0
    private const val CALORIES = 300.0
    private const val MINUTES = 30.0
    private const val DAYS = 5.0

    fun getKilometerLevel(kilometers: Double): Pair<Int, Double> {
        var level = 1
        while (kilometers >= KILOMETERS * level)
            level++
        return Pair(level, (KILOMETERS * level))
    }

    fun getCalorieLevel(calories: Int): Pair<Int, Double> {
        var level = 1
        while (calories >= CALORIES * level)
            level++
        return Pair(level, CALORIES * level)
    }

    fun getMinuteLevel(minutes: Int): Pair<Int, Double> {
        var level = 1
        while (minutes >= MINUTES * level)
            level++
        return Pair(level, MINUTES * level)
    }

    fun getDayLevel(days: Int): Pair<Int, Double> {
        var level = 1
        while (days >= DAYS * level)
            level++
        return Pair(level, DAYS * level)
    }
}
