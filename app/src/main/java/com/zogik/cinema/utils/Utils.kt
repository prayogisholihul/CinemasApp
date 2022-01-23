package com.zogik.cinema.utils

import android.content.Context
import android.view.View
import android.widget.Toast

object Utils {
    fun showToast(ctx: Context, text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    fun viewVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    fun viewGone(view: View) {
        view.visibility = View.GONE
    }
}