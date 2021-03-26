package com.csosa.fntest

import androidx.test.espresso.IdlingRegistry
import com.csosa.fntest.helpers.FNowRequestDispatcher
import com.csosa.fntest.idlingresource.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

open class BaseTest : KoinTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    open fun setup() {

        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = FNowRequestDispatcher()
        mockWebServer.start(8082)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }


    @After
    open fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}
