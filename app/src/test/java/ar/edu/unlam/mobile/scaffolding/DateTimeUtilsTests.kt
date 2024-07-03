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
    fun whenProvidingTime_FormatsCorrectly() {
        val providedTime = 1720040288000
        val expectedTime = "08:58:08"
        val actualTime = DateTimeUtils.formatTime(providedTime)
        Assert.assertEquals(expectedTime, actualTime)
    }
}
