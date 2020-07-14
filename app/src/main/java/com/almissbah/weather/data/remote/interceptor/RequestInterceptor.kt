package com.almissbah.weather.data.remote.interceptor

import com.almissbah.weather.utils.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class RequestInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalHttpUrl: HttpUrl = request.url()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("appid", API_KEY)
            .build()
        val modifiedRequest = request.newBuilder().url(url)

        return chain.proceed(modifiedRequest.build())
    }
}