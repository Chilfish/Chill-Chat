package top.chilfish.chillchat.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formattedTime(time: Date, format: String = "MM-dd HH:mm"): String? {
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(time)
}