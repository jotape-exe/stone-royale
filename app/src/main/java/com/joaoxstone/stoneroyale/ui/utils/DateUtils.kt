package com.joaoxstone.stoneroyale.ui.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

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
}