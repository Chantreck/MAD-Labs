package com.tsu.itindr.model.utils

import android.content.Context
import android.widget.Toast

object MessageShower {
    fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context?, message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}