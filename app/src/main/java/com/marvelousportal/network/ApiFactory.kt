package com.marvelousportal.network

import android.content.ContentValues.TAG
import android.support.annotation.NonNull
import android.util.Log
import com.marvelousportal.utils.Constant.Companion.BASE_URL
import com.marvelousportal.utils.Constant.Companion.ERROR_CODE_GENERAL
import com.marvelousportal.utils.Constant.Companion.ERROR_CODE_INTERNET_NOT_AVAILABLE
import com.marvelousportal.utils.Constant.Companion.ERROR_MESSAGE_INTERNET_NOT_AVAILABLE
import com.marvelousportal.utils.Constant.Companion.ERROR_MESSAGE_SOMETHING_WRONG
import com.marvelousportal.utils.Constant.Companion.RES_SUCCESS
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

object ApiFactory {

    fun create(): APIService {
        /*val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(APIService::class.java)*/
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        //Building retrofit
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
        return retrofit.create(APIService::class.java)
    }


    /**
     * Get the instance of the retrofit [APIService].
     *
     * @return [APIService]
     */
    fun getApiService(): APIService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        //Building retrofit
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()
        return retrofit.create(APIService::class.java)
    }

    /**
     * Make in api call on the separate thread.
     *
     * @param observable [Observable]
     * @return [Disposable]
     * @see [How to handle exceptions and errors?](https://github.com/ReactiveX/RxJava/issues/4942)
     */
    fun makeApiCall(@NonNull observable: Observable<*>,
                    @NonNull listener: ResponseListener): Disposable? {
        return makeApiCall(observable, listener, false)
    }

    private fun makeApiCall(observable: Observable<*>, listener: ResponseListener, b: Boolean): Disposable? {
        //noinspection unchecked
        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response ->
                    val jobj = JSONObject(response.toString())
                    if (jobj.getString("response_code") == RES_SUCCESS) {
                        listener.onSuccess(jobj)
                    } else {
                        listener.onError(ERROR_CODE_GENERAL, jobj.getJSONArray("error").get(0).toString())
                    }
                }, { throwable ->
                    val errorCode: Int
                    var message: String? = null

                    Log.d(TAG, "accept: " + throwable.message)

                    if (throwable is HttpException) { //Error frm the server.
                        val responseBody = throwable
                                .response()
                                .errorBody()
                        if (responseBody == null) {
                            //Nothing in response body
                            errorCode = ERROR_CODE_GENERAL
                            message = ERROR_MESSAGE_SOMETHING_WRONG
                        } else {
                            //                                String responseStr = Utils.getStringFromStream(responseBody.byteStream());
                            errorCode = throwable.code()
                            message = responseBody.string()
                        }
                    } else if (throwable is UnknownHostException) {
                        //Internet not available.
                        errorCode = ERROR_CODE_INTERNET_NOT_AVAILABLE
                        message = ERROR_MESSAGE_INTERNET_NOT_AVAILABLE
                    } else {
                        //Any other exception
                        errorCode = ERROR_CODE_GENERAL
                        message = ERROR_MESSAGE_SOMETHING_WRONG
                    }
                })
    }
}