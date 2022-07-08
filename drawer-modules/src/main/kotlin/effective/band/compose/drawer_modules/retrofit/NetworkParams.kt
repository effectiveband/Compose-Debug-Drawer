package effective.band.compose.drawer_modules.retrofit

object NetworkParams {
    val varianceValues: List<Int> = listOf(20, 40, 60)
    val failureValues: List<Int> = listOf(0, 1, 3, 10, 25, 50, 75, 100)
    val errorPercentValues: List<Int> = listOf(0, 1, 3, 10, 25, 50, 75, 100)
    val errorCodeValues: List<Int> = listOf(400, 401, 403, 500, 501, 503, 504)

    enum class DelayVariant(val delayValue: Long) {
        Low(delayValue = 5000),
        Medium(delayValue = 2000),
        High(delayValue = 250),
    }
}
