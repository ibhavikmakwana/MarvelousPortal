package com.marvelousportal.utils

/**
 * Created by Ahmad Shubita on 8/1/17.
 */

interface Constant {
    companion object {
        val BASE_URL = "http://gateway.marvel.com/"
        val PUBLIC_KEY = "bae8d2281a0d569b0cfd5fbc7cd6ee6f"
        val PRIVATE_KEY = "8ec9a86b449cfdacb2a0129a896ea35ef6fe98fc"


        //Error codes
        val ERROR_CODE_GENERAL = 9856                     //Generalize error code if the error in unrecognized.
        val ERROR_CODE_INTERNET_NOT_AVAILABLE = 5646      //Error code when internet connection is not available.
        val ERROR_CODE_EMAIL_NOT_VERIFIED = 8544          //Error code when email is not verified.
        val ERROR_CODE_UNAUTHORIZED = 4001                 //Error code when email is not verified.
        private val TAG = "RetrofitHelper"
        //Error messages.
        val ERROR_MESSAGE_INTERNET_NOT_AVAILABLE = "Internet is not available. Please try again."
        val ERROR_MESSAGE_SOMETHING_WRONG = "Something went wrong."
        //Result code success.
        val RES_SUCCESS = "1111"

        val COMICS = "comics"
        val CHARACTERS = "character"
        val EVENTS = "events"
        val SERIES = "series"
    }
}