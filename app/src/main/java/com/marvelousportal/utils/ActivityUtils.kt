package com.marvelousportal.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

object ActivityUtils {

    fun setActionBarColor(appCompatActivity: AppCompatActivity, colorId: Int) {
        val actionBar = appCompatActivity.supportActionBar
        val colorDrawable = ColorDrawable(getColor(appCompatActivity, colorId))
        actionBar!!.setBackgroundDrawable(colorDrawable)
    }

    fun getColor(context: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }
}