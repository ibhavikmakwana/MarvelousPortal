package com.marvelousportal.network

import android.os.Environment
import android.util.Log
import com.marvelousportal.utils.Constant.Companion.BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ApiFactory {

    private var cacheSize = 10 * 1024 * 1024 // 10 MB
    private var cache = Cache(getCacheDir(), cacheSize.toLong())

    private fun getCacheDir(): File? {
        /*Log.i("FILE", Environment.getExternalStorageDirectory().toString() + "/MarvelousPortalCache/")
        return File(Environment.getExternalStorageDirectory().toString() + "/MarvelousPortalCache/")*/
        val root = File(Environment.getExternalStorageDirectory().toString() + File.separator + "MarvelousPortalCache" + File.separator)
        root.mkdirs()
        Log.i("FILE", root.toString())
        return File(root.toString())
    }

    fun create(): APIService {
        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        interceptor.level = HttpLoggingInterceptor.Level.BODY


        //Building retrofit
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
        return retrofit.create(APIService::class.java)
    }

    fun makeApiCall() {

    }
}