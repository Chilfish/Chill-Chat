package top.chilfish.chillchat.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formattedTime(time: Any, format: String = "MM-dd HH:mm"): String {
    val sdf = SimpleDateFormat(format, Locale.getDefault())

    val date = when (time) {
        is Long -> Date(time)
        is Date -> time
        is String -> sdf.parse(time)
        else -> return "error time"
    }
    return sdf.format(date)
}