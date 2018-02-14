package com.marvelousportal.network

import com.marvelousportal.models.Model
import io.reactivex.Observable
import retrofit2.http.*

interface APIService {

    @GET("v1/public/characters")
    fun fetchCharacters(@Query("ts") ts: String,
                        @Query("apikey") apiKey: String,
                        @Query("hash") hash: String,
                        @Query("limit") limit: Int,
                        @Query("offset") offset: Int): Observable<Model>

    @GET("v1/public/comics")
    fun fetchComics(@Query("ts") ts: String,
                    @Query("apikey") apiKey: String,
                    @Query("hash") hash: String,
                    @Query("limit") limit: Int): Observable<Model>

    @GET("v1/public/comics/{comicID}")
    fun fetchComicsDetail(@Path("comicID") id: Int,
                          @Query("ts") ts: String,
                          @Query("apikey") apiKey: String,
                          @Query("hash") hash: String): Observable<Model>

    @GET("v1/public/characters/{characterID}")
    fun fetchCharacterDetail(@Path("characterID") id: Int,
                             @Query("ts") ts: String,
                             @Query("apikey") apiKey: String,
                             @Query("hash") hash: String): Observable<Model>

    @GET("v1/public/series")
    fun fetchSeries(@Query("ts") ts: String,
                    @Query("apikey") apiKey: String,
                    @Query("hash") hash: String,
                    @Query("limit") limit: Int): Observable<Model>

    @GET("v1/public/events")
    fun fetchEvents(@Query("ts") ts: String,
                    @Query("apikey") apiKey: String,
                    @Query("hash") hash: String,
                    @Query("limit") limit: Int): Observable<Model>

    @GET("v1/public/series/{seriesID}")
    fun fetchSeriesDetail(@Path("seriesID") id: Int,
                          @Query("ts") ts: String,
                          @Query("apikey") apiKey: String,
                          @Query("hash") hash: String): Observable<Model>

    @GET("v1/public/events/{eventId}")
    fun fetchEventsDetail(@Path("eventId") id: Int,
                          @Query("ts") ts: String,
                          @Query("apikey") apiKey: String,
                          @Query("hash") hash: String): Observable<Model>


    @GET("v1/public/characters")
    fun searchCharacters(@Query("ts") ts: String,
                         @Query("apikey") apiKey: String,
                         @Query("hash") hash: String,
                         @Query("nameStartsWith") nameStartsWith: String,
                         @Query("limit") limit: Int): Observable<Model>

    @GET("v1/public/comics")
    fun searchComics(@Query("ts") ts: String,
                     @Query("apikey") apiKey: String,
                     @Query("hash") hash: String,
                     @Query("titleStartsWith") nameStartsWith: String,
                     @Query("limit") limit: Int): Observable<Model>

    @GET("v1/public/events")
    fun searchEvents(@Query("ts") ts: String,
                     @Query("apikey") apiKey: String,
                     @Query("hash") hash: String,
                     @Query("nameStartsWith") nameStartsWith: String,
                     @Query("limit") limit: Int): Observable<Model>

    @GET("v1/public/series")
    fun searchSeries(@Query("ts") ts: String,
                     @Query("apikey") apiKey: String,
                     @Query("hash") hash: String,
                     @Query("titleStartsWith") nameStartsWith: String,
                     @Query("limit") limit: Int): Observable<Model>

    @GET
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    fun getDetailItemListing(@Url url: String): Observable<Model>
}