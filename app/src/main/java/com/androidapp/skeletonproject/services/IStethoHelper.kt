package com.androidapp.skeletonproject.services

import android.content.Context
import okhttp3.OkHttpClient

interface IStethoHelper {
    fun init(context: Context)
    fun configureInterceptor(httpClientBuilder: OkHttpClient.Builder): OkHttpClient.Builder
}
