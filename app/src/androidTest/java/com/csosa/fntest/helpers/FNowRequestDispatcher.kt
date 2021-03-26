package com.csosa.fntest.helpers

import com.csosa.fntest.sample.google_matrix
import com.csosa.fntest.sample.my_taxi
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

/**
 * Handles Requests from mock web server
 */
internal class FNowRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {

        return when {
            request.path?.contains(MY_TAXI_URL)!! -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(my_taxi)

            }
            request.path?.contains(GOOGLE_MATRIX_API_URL)!! -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(google_matrix)
            }
            else -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
            }
        }

    }

}
