package com.marvelousportal.network

import org.json.JSONObject

/**
 * Created by Keval on 01-Jun-17.
 * Listener to get the response from the retrofit api call.
 *
 * @author [&#39;https://github.com/kevalpatel2106&#39;]['https://github.com/kevalpatel2106']
 */

interface ResponseListener {

    /**
     * This callback will be called when there is success response from the api. (i.e. "status": "OK")
     *
     * @param jsonResult [jsonResult] from the api response.
     */
    fun onSuccess(jsonResult: JSONObject)

    /**
     * This callback will be called when any error occurs. (e.g. "status" : "NOK", internet not available etc.)
     *
     * @param errorCode Error code.
     * @param message   Error messages.
     */
    fun onError(errorCode: Int, message: String)
}
