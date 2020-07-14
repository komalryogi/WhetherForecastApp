package com.almissbah.weather.utils

import android.view.View

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.unHide() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun String.trimAndCapitalFirst(): String {
    return trim().toLowerCase().capitalize()
}