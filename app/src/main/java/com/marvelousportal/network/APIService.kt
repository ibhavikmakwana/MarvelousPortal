package com.marvelousportal.network

import com.marvelousportal.models.Model
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("v1/public/characters")
    fun fetchCharacters(@Query("ts") ts: String,
                        @Query("apikey") apiKey: String,
                        @Query("hash") hash: String): Observable<Model>

    @GET("v1/public/comics")
    fun fetchComics(@Query("ts") ts: String,
                    @Query("apikey") apiKey: String,
                    @Query("hash") hash: String): Observable<Model>

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
}