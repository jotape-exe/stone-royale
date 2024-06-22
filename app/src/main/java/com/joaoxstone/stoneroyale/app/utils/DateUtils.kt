package com.joaoxstone.stoneroyale.app.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


object DateUtils {
    fun convertDate(isoDateString: String): String {
        val zonedDateTime = ZonedDateTime.parse(
            isoDateString,
            DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSX")
        )
        val localZoneId = ZoneId.systemDefault()
        val localDateTime = zonedDateTime.withZoneSameInstant(localZoneId)
        val desiredFormat = DateTimeFormatter.ofPattern("dd/MM HH:mm")
        val formattedDate = localDateTime.format(desiredFormat)

        return formattedDate
    }

    fun timeAgo(inputDate: String): String {

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSX")

        val parsedDate = ZonedDateTime.parse(inputDate, formatter)

        val now = ZonedDateTime.now(parsedDate.zone)

        val minutesBetween = ChronoUnit.MINUTES.between(parsedDate, now)
        val hoursBetween = ChronoUnit.HOURS.between(parsedDate, now)
        val daysBetween = ChronoUnit.DAYS.between(parsedDate, now)
        val monthsBetween = ChronoUnit.MONTHS.between(parsedDate, now)

        return when {
            minutesBetween < 60 -> "<1 hora atrás"
            hoursBetween < 24 -> "${hoursBetween}h atrás"
            daysBetween < 30 -> "${daysBetween} dia${if (daysBetween > 1) "s" else ""} atrás"
            monthsBetween < 3 -> "${monthsBetween} mês${if (monthsBetween > 1) "es" else ""} atrás"
            else -> ">3 meses atrás"
        }
    }

}