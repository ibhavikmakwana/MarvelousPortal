package com.marvelousportal.repository

import android.content.Context
import android.util.Log
import com.marvelousportal.R
import com.marvelousportal.models.Model
import com.marvelousportal.network.APIService
import com.marvelousportal.utils.ActivityUtils.getHash
import com.marvelousportal.utils.ActivityUtils.getTimestamp
import io.reactivex.Observable

/**
 * Created by Bhavik Makwana on 2/6/2018.
 */
class MarvelRepository(private val apiService: APIService, private val mContext: Context) {
    private val MAX_FETCH_LIMIT = 15

    fun getCharacters(offset: Int): Observable<Model>? {
        return Observable.concatArray(getCharactersFromApi(offset))
    }

    fun getComics(): Observable<Model>? {
        return Observable.concatArray(getComicsFromApi())
    }

    fun getEvents(): Observable<Model>? {
        return Observable.concatArray(getEventsFromApi())
    }

    fun getSeries(): Observable<Model>? {
        return Observable.concatArray(getSeriesFromApi())
    }

    fun getSearchedCharacters(query: String): Observable<Model>? {
        return Observable.concatArray(getSearchedCharactersFromApi(query))
    }

    fun getSearchedComics(query: String): Observable<Model>? {
        return Observable.concatArray(getSearchedComicsFromApi(query))
    }

    fun getSearchedEvents(query: String): Observable<Model>? {
        return Observable.concatArray(getSearchedEventsFromApi(query))
    }

    fun getSearchedSeries(query: String): Observable<Model>? {
        return Observable.concatArray(getSearchedSeriesFromApi(query))
    }

    private fun getCharactersFromApi(offset: Int): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchCharacters(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), MAX_FETCH_LIMIT, offset).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getComicsFromApi(): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchComics(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getEventsFromApi(): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchEvents(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getSeriesFromApi(): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchSeries(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getSearchedCharactersFromApi(query: String): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.searchCharacters(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), query, MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getSearchedComicsFromApi(query: String): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.searchComics(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), query, MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getSearchedEventsFromApi(query: String): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.searchEvents(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), query, MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    private fun getSearchedSeriesFromApi(query: String): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.searchSeries(timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext), query, MAX_FETCH_LIMIT).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    fun getCharacterDetailFromApi(id: Int): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchCharacterDetail(id, timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext)).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    fun getSeriesDetailFromApi(id: Int): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchSeriesDetail(id, timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext)).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    fun getComicDetailFromApi(id: Int): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchComicsDetail(id, timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext)).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    fun getEventDetailFromApi(id: Int): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.fetchEventsDetail(id, timeStamp, mContext.resources.getString(R.string.public_key), getHash(timeStamp, mContext)).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

    fun getDetailItemListing(url: String): Observable<Model>? {
        val timeStamp = getTimestamp()
        return apiService.getDetailItemListing(url + "?ts=" + timeStamp + "&apikey=" + mContext.resources.getString(R.string.public_key) + "&hash=" + getHash(timeStamp, mContext)).doOnNext {
            Log.d("Success", "Dispatching $it characters from API...")
        }
    }

}