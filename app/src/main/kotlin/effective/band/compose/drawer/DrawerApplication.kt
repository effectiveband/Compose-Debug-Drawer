package effective.band.compose.drawer

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class DrawerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}