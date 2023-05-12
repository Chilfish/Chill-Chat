package top.chilfish.chillchat.utils

import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder

inline fun <reified T> toJson(data: T): String = URLEncoder.encode(Gson().toJson(data), "utf-8")

inline fun <reified T> toData(json: String?): T =
    Gson().fromJson(
        URLDecoder.decode(json, "utf-8"),
        T::class.java
    )