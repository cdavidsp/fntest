package com.csosa.fntest.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.csosa.fntest.data.local.FNTestDatabase
import com.csosa.fntest.data.local.dao.VehiclesDao
import com.csosa.fntest.data.local.dao.VehiclesQueriesDao
import com.csosa.fntest.data.preferences.AppPreferences
import com.csosa.fntest.data.remote.api.FNTestApiService
import com.csosa.fntest.data.helpers.FNTestRequestDispatcher
import com.csosa.fntest.data.remote.api.GoogleMapsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

internal open class BaseTest {

    private lateinit var mockWebServer: MockWebServer

    lateinit var fnTestApiService: FNTestApiService

    lateinit var googleMapsApiService: GoogleMapsApiService

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var loggingInterceptor: HttpLoggingInterceptor

    private lateinit var db: FNTestDatabase

    protected lateinit var vehiclesQueriesDao: VehiclesQueriesDao

    protected lateinit var vehiclesDao: VehiclesDao

    protected lateinit var appPreferences: AppPreferences


    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = FNTestRequestDispatcher()
        mockWebServer.start()
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkHttpClient(loggingInterceptor)

        val baseUrl = mockWebServer.url("/")
        fnTestApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(FNTestApiService::class.java)


        googleMapsApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GoogleMapsApiService::class.java)


        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FNTestDatabase::class.java).build()
        vehiclesDao = db.vehiclesDao()
        vehiclesQueriesDao = db.vehicleQueryDao()

        appPreferences = AppPreferences(context)
    }

    @After
    @Throws(IOException::class)
    open fun tearDown() {
        mockWebServer.shutdown()
        runBlocking(Dispatchers.IO) {
            db.clearAllTables()
        }

        db.close()
    }

    private fun buildOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

}
