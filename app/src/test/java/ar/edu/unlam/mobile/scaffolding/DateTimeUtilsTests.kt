package ar.edu.unlam.mobile.scaffolding

import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.utils.DateTimeUtils
import org.junit.Assert
import org.junit.Test

class DateTimeUtilsTests {
    @Test
    fun whenProvidingDate_FormatsCorrectly() {
        val providedDate = MockEntities.route.date
        val expectedFormattedDate = "01/07"
        val actualDate = DateTimeUtils.formatDate(providedDate)
        Assert.assertEquals(expectedFormattedDate, actualDate)
    }

    @Test
    fun whenProvidingTime_FormatsIncorrectly() {
        val providedTime = 1720042583000
        val expectedTime = "10:36:23"
        val actualTime = DateTimeUtils.formatTime(providedTime)
        Assert.assertNotEquals(expectedTime, actualTime)
    }
}
