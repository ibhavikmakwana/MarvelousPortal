package com.marvelousportal.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.marvelousportal.R
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and

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

    @SuppressLint("SimpleDateFormat")
    fun getTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(Date())
    }

    fun getHash(timeStamp: String, mContext: Context): String {
        try {
            val hashString = timeStamp + mContext.resources.getString(R.string.private_key) + mContext.resources.getString(R.string.public_key)
            val md = MessageDigest.getInstance("MD5")
            md.update(hashString.toByteArray())
            val byteData = md.digest()
            //convert the byte to hex format method 1
            val sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(Integer.toString((byteData[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }

            //convert the byte to hex format method 2
            val hexString = StringBuffer()
            for (i in byteData.indices) {
                val hex = Integer.toHexString(0xff and byteData[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }

            return hexString.toString()

        } catch (e: Exception) {
            // TODO: handle exception
        }

        return ""
    }
}