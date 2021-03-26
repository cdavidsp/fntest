package com.csosa.fntest.data.helpers

import com.csosa.fntest.data.helpers.Constants.GOOGLE_MATRIX_API_URL
import com.csosa.fntest.data.helpers.Constants.MY_TAXI_URL
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

/**
 * Handles Requests from mock web server
 */
internal class FNTestRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {

        when {
            request.path?.contains(MY_TAXI_URL)!! -> {
                return MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/my_taxi.json"))

            }
            request.path?.contains(GOOGLE_MATRIX_API_URL)!! -> {
                return  MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/google_matrix.json"))
            }
            else -> {
                throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
            }
        }
    }

}
