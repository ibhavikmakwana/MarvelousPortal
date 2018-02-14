package com.marvelousportal.viewmodels

import android.util.Log
import com.marvelousportal.models.Model
import com.marvelousportal.network.ErrorHandler.getErrorCode
import com.marvelousportal.network.ErrorHandler.getErrorMessage
import com.marvelousportal.repository.MarvelRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class CharactersViewModel(private val marvelRepository: MarvelRepository) {
    fun getCharacters(offset: Int): Observable<Model>? {
        return marvelRepository.getCharacters(offset)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Model(getErrorCode(it), getErrorMessage(it), null, null, null, null, null)
                }
    }

    fun getSearchedCharacters(query: String): Observable<Model>? {
        return marvelRepository.getSearchedCharacters(query)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(getErrorCode(it), getErrorMessage(it), null, null, null, null, null)
                }
    }
}

/*
var message: String
var errorCode: Int

if (it is HttpException) { //Error frm the server.
    val responseBody = it
            .response()
            .errorBody()
    if (responseBody == null) {
        //Nothing in response body
        errorCode = ERROR_CODE_GENERAL
        message = ERROR_MESSAGE_SOMETHING_WRONG
    } else {
        //                                String responseStr = Utils.getStringFromStream(responseBody.byteStream());
        errorCode = it.code()
        message = responseBody.string()
    }
} else if (it is UnknownHostException) {
    //Internet not available.
    errorCode = ERROR_CODE_INTERNET_NOT_AVAILABLE
    message = ERROR_MESSAGE_INTERNET_NOT_AVAILABLE
} else {
    //Any other exception
    errorCode = ERROR_CODE_GENERAL
    message = ERROR_MESSAGE_SOMETHING_WRONG
}*/
