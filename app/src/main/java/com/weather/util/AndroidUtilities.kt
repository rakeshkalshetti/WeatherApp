package com.weather.util

import java.text.SimpleDateFormat
import java.util.*

class AndroidUtilities {

    companion object {

        fun toDate(timeStamp: Long): String {
            val sdf = SimpleDateFormat("EEE,d MMM", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(Date(timeStamp * 1000))
        }

        fun toTime(timeStamp: Long): String {
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            return sdf.format(Date(timeStamp * 1000))
        }


    }
}