package ar.edu.unlam.mobile.scaffolding.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

val LOCALE_ARGENTINA = Locale("es", "AR")

object DateTimeUtils {
    fun formatTime(timeInMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
        return String.format(LOCALE_ARGENTINA, "%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM", LOCALE_ARGENTINA).format(date)
    }
}
