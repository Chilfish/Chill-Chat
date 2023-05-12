package top.chilfish.chillchat.utils

import com.google.gson.Gson
import java.net.URLDecoder

inline fun <reified T> toJson(data: T): String =
    Gson()
        .toJson(data)
        .replace("/", "%2F")

inline fun <reified T> toData(json: String?): T =
    Gson().fromJson(
        URLDecoder.decode(json, "utf-8"),
        T::class.java
    )