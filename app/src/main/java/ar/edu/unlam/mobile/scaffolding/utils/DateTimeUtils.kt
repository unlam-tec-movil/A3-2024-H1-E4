package ar.edu.unlam.mobile.scaffolding.utils

import java.util.concurrent.TimeUnit

object DateTimeUtils {
    fun formatTime(timeInMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
