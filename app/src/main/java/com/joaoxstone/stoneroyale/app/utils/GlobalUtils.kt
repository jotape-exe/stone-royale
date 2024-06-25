package com.joaoxstone.stoneroyale.app.utils

import android.content.Context
import android.widget.Toast
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object GlobalUtils {

    fun timeAgo(inputDate: String): String {

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSX")
        val parsedDate = ZonedDateTime.parse(inputDate, formatter)
        val now = ZonedDateTime.now(parsedDate.zone)

        val minutesBetween = ChronoUnit.MINUTES.between(parsedDate, now)
        val hoursBetween = ChronoUnit.HOURS.between(parsedDate, now)
        val daysBetween = ChronoUnit.DAYS.between(parsedDate, now)
        val monthsBetween = ChronoUnit.MONTHS.between(parsedDate, now)

        return when {
            minutesBetween < 60 -> "<1h atrás"
            hoursBetween < 24 -> "${hoursBetween}h atrás"
            daysBetween < 30 -> "$daysBetween dia${if (daysBetween > 1) "s" else ""} atrás"
            monthsBetween < 3 -> "$monthsBetween mês${if (monthsBetween > 1) "es" else ""} atrás"
            else -> ">3 meses atrás"
        }
    }

    fun formattedTag(tag: String): String {
        return "#${tag.uppercase().replace("O", "0").replace(" ", "")}"
    }

    fun makeToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}