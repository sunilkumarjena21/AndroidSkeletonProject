package com.androidapp.skeletonproject.utils

import android.util.Base64
import timber.log.Timber
import java.io.UnsupportedEncodingException

@Throws(Exception::class)
fun decodeJwt(JWTEncoded: String): String {
    val split = JWTEncoded.split(".").toTypedArray()
    Timber.d("Body: %s", getJson(split[1]))
    val jsonBody = getJson(split[1])
    return jsonBody
}

@Throws(UnsupportedEncodingException::class)
fun getJson(strEncoded: String): String {
    val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
    return String(decodedBytes)
}
