package com.marvelousportal.network

import com.marvelousportal.utils.Constant.Companion.ERROR_CODE_GENERAL
import com.marvelousportal.utils.Constant.Companion.ERROR_CODE_INTERNET_NOT_AVAILABLE
import com.marvelousportal.utils.Constant.Companion.ERROR_MESSAGE_INTERNET_NOT_AVAILABLE
import com.marvelousportal.utils.Constant.Companion.ERROR_MESSAGE_SOMETHING_WRONG
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * Created by Bhavik Makwana on 2/7/2018.
 */
object ErrorHandler {
    fun getErrorMessage(it: Throwable): String {
        val message: String

        when (it) {
            is HttpException -> { //Error frm the server.
                val responseBody = it
                        .response()
                        .errorBody()
                message = if (responseBody == null) {
                    //Nothing in response body
                    ERROR_MESSAGE_SOMETHING_WRONG
                } else {
                    responseBody.string()
                }
            }
            is UnknownHostException -> //Internet not available.
                message = ERROR_MESSAGE_INTERNET_NOT_AVAILABLE
            else -> //Any other exception
                message = ERROR_MESSAGE_SOMETHING_WRONG
        }
        return message
    }

    fun getErrorCode(it: Throwable): Int {
        val errorCode: Int

        when (it) {
            is HttpException -> { //Error frm the server.
                val responseBody = it
                        .response()
                        .errorBody()
                errorCode = if (responseBody == null) {
                    //Nothing in response body
                    ERROR_CODE_GENERAL
                } else {
                    it.code()
                }
            }
            is UnknownHostException -> //Internet not available.
                errorCode = ERROR_CODE_INTERNET_NOT_AVAILABLE
            else -> //Any other exception
                errorCode = ERROR_CODE_GENERAL
        }

        return errorCode
    }
}