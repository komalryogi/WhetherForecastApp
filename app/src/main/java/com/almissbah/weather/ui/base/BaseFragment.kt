package com.almissbah.weather.ui.base

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.almissbah.weather.utils.hide
import com.almissbah.weather.utils.unHide
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {
    var mProgressBar: ProgressBar? = null


    fun showSnackbar(view: View, msg: String) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun showLoading() {
        mProgressBar?.unHide()
    }

    fun hideLoading() {
        mProgressBar?.hide()
    }

}