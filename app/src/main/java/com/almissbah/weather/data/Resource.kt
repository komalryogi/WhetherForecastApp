package com.almissbah.weather.data

data class Resource<T, M>(val payload: T?, val action: M?, val message: String)