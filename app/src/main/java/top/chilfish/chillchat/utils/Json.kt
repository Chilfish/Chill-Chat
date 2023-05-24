package top.chilfish.chillchat.utils

import com.google.gson.Gson
import java.net.URLDecoder

inline fun <reified T> toJson(data: T): String =
    Gson().toJson(data)

inline fun <reified T> toData(json: String?): T =
    Gson().fromJson(json, T::class.java)