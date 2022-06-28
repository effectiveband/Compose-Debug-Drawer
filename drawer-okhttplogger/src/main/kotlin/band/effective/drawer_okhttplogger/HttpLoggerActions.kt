package band.effective.drawer_okhttplogger

import okhttp3.logging.HttpLoggingInterceptor

class HttpLoggerActions(private val httpLogger: HttpLogger) {

    fun getStoredLevel(): HttpLoggingInterceptor.Level {
        return HttpLoggingInterceptor.Level.values()[httpLogger.getStoredLevelPosition()]
    }

    fun setLevel(position: Int) {
        httpLogger.setLevel(position)
    }
}