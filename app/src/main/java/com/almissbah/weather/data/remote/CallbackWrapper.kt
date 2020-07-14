package com.almissbah.weather.data.remote

import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.*
import java.net.SocketTimeoutException

open class CallbackWrapper<T>(private val callback: HttpCallback<T>) :
    DisposableObserver<Response<T>>() {

    private fun onSuccess(t: Response<T>) {
        if (t.code() == HTTP_OK) {
            callback.onSuccess(t.body())
        } else if (t.code() >= HTTP_NOT_FOUND) {
            callback.onNotFound()
        } else if (t.code() >= HTTP_BAD_REQUEST) {
            callback.onServerError()
        }
    }

    override fun onNext(t: Response<T>) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        when (e) {
            is HttpException -> {
                callback.onServerError()
            }
            is SocketTimeoutException -> {
                callback.onNetworkError()
            }
            is IOException -> {
                callback.onNetworkError()
            }
            else -> {
                callback.onNetworkError()
            }
        }
    }

    override fun onComplete() {}


    interface HttpCallback<T> {
        fun onSuccess(t: T?)
        fun onNetworkError()
        fun onServerError()
        fun onNotFound()
    }

}