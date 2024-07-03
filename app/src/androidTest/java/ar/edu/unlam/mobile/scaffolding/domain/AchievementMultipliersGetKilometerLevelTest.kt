package ar.edu.unlam.mobile.scaffolding.domain

import ar.edu.unlam.mobile.scaffolding.domain.AchievementMultipliers.getKilometerLevel
import org.junit.Assert.*
import org.junit.Test

class AchievementMultipliersGetKilometerLevelTest{
    @Test
    fun testGetKilometerLevelAtLevel1() {
        val result = getKilometerLevel(2.0)
        assertEquals(Pair(1, 5.0), result)
    }

    @Test
    fun testGetKilometerLevelAtBoundary() {
        val result = getKilometerLevel(5.0)
        assertEquals(Pair(2, 10.0), result)
    }

    @Test
    fun testGetKilometerLevelAtLevel2() {
        val result = getKilometerLevel(7.0)
        assertEquals(Pair(2, 10.0), result)
    }

    @Test
    fun testGetKilometerLevelAboveLevel2() {
        val result = getKilometerLevel(12.0)
        assertEquals(Pair(3, 15.0), result)
    }

    @Test
    fun testGetKilometerLevelAtLargeDistance() {
        val result = getKilometerLevel(45.0)
        assertEquals(Pair(10, 50.0), result)
    }
}