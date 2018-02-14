package com.marvelousportal.viewmodels

import android.util.Log
import com.marvelousportal.models.Model
import com.marvelousportal.network.ErrorHandler
import com.marvelousportal.repository.MarvelRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Bhavik Makwana on 2/9/2018.
 */
class DetailViewModel(private val marvelRepository: MarvelRepository) {
    fun getCharactersDetail(id: Int): Observable<Model>? {
        return marvelRepository.getCharacterDetailFromApi(id)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(ErrorHandler.getErrorCode(it), ErrorHandler.getErrorMessage(it), null, null, null, null, null)
                }
    }

    fun getComicsDetail(id: Int): Observable<Model>? {
        return marvelRepository.getComicDetailFromApi(id)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(ErrorHandler.getErrorCode(it), ErrorHandler.getErrorMessage(it), null, null, null, null, null)
                }
    }

    fun getEventDetail(id: Int): Observable<Model>? {
        return marvelRepository.getEventDetailFromApi(id)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(ErrorHandler.getErrorCode(it), ErrorHandler.getErrorMessage(it), null, null, null, null, null)
                }
    }

    fun getSeriesDetail(id: Int): Observable<Model>? {
        return marvelRepository.getSeriesDetailFromApi(id)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(ErrorHandler.getErrorCode(it), ErrorHandler.getErrorMessage(it), null, null, null, null, null)
                }
    }

    fun getListItems(url: String): Observable<Model>? {
        return marvelRepository.getDetailItemListing(url)?.debounce(400, TimeUnit.MILLISECONDS)?.map {
            Log.d("Success", "Mapping characters to UIData...")
            Model(it.code, it.status, it.copyright, it.attributionText, it.attributionHTML, it.etag, it.data)
        }?.onErrorReturn {
                    Log.d("Error", it.localizedMessage)
                    Model(ErrorHandler.getErrorCode(it), ErrorHandler.getErrorMessage(it), null, null, null, null, null)
                }
    }
}