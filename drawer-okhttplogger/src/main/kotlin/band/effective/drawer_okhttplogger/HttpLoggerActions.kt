package band.effective.drawer_okhttplogger

class HttpLoggerActions(private val httpLogger: HttpLogger){

    fun setLevel(position: Int){
        httpLogger.setLevel(position)
    }
}