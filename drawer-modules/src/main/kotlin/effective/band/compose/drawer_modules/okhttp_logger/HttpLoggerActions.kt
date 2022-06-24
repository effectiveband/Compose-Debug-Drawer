package effective.band.compose.drawer_modules.okhttp_logger

class HttpLoggerActions(private val httpLogger: HttpLogger){

    fun setLevel(position: Int){
        httpLogger.setLevel(position)
    }
}