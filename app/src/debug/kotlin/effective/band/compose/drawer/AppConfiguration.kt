package effective.band.compose.drawer

import android.app.Application
import effective.band.compose.drawer_modules.okhttp.HttpLogger
import effective.band.compose.drawer_modules.retrofit.DebugRetrofitConfig
import effective.band.compose.drawer_modules.retrofit.Endpoint
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import effective.band.compose.drawer.network.GamesApi
import effective.band.compose.drawer.network.HttpConfiguration
import effective.band.compose.drawer.network.HttpConfiguration.API_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

/**
 * Debug configuration for the sample app.
 */
object AppConfiguration {

    private lateinit var app: Application

    private val endpoints = listOf(
        Endpoint("Stage", API_URL, isMock = false),
        Endpoint("Production", API_URL, isMock = false),
        Endpoint("Mock", "http://localhost/mock/", isMock = true),
    )

    private val networkBehavior = NetworkBehavior.create()
    val httpLogger by lazy { HttpLogger(app) }
    val debugRetrofitConfig by lazy { DebugRetrofitConfig(app, endpoints, networkBehavior) }

    val api: GamesApi by lazy { createApi() }

    fun init(app: Application) {
        AppConfiguration.app = app
    }

    private fun createApi(): GamesApi {
        val currentEndpoint: Endpoint = debugRetrofitConfig.currentEndpoint
        val httpClient = HttpConfiguration.client.newBuilder()
            .addInterceptor(httpLogger.interceptor)
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(currentEndpoint.url)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        if (currentEndpoint.isMock) {
            val mockRetrofit =
                MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build()
            return MockGamesApi(mockRetrofit)
        }

        return retrofit.create(GamesApi::class.java)
    }
}
