package effective.band.compose.drawer

import android.app.Activity
import android.app.Application
import android.view.ViewGroup
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import effective.band.compose.drawer.network.GamesApi
import effective.band.compose.drawer.network.HttpConfiguration
import effective.band.compose.drawer.network.HttpConfiguration.API_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Release configuration for the sample app.
 */
object AppConfiguration {

    val api: GamesApi

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        api = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(HttpConfiguration.client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GamesApi::class.java)
    }

    // Keep this method to match debug implementation.
    fun init(app: Application) {}
}
