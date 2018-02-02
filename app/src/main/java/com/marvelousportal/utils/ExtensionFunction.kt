@file:JvmName("Ext")

package com.marvelousportal.utils

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import java.io.ByteArrayOutputStream


/**
 * Created by Lalit on 20-Jul-17.
 */

fun ViewGroup.inflate(layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun AppCompatActivity.toast(string: String) = Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()

fun AppCompatActivity.log(string: String) = Log.d(this.localClassName.toString(), string)

fun EditText.isEmpty() = this.text.isEmpty()

fun EditText.getTrimmedText() = this.text.toString().trim()

fun EditText.isPersonNameValid() = this.text.matches("[a-zA-Z\\s]+".toRegex())

fun EditText.isEmailValid() = this.text.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())

fun EditText.isPasswordValid() = this.text.matches("[a-zA-Z0-9._@#-]{6,12}+".toRegex())

fun EditText.showError(string: String) {
    this.error = string
    this.requestFocus()
}

fun Bitmap.toByte(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}