package band.effective.drawer_retrofit

object NetworkParams {
    val delayValues: List<Long> = listOf(250, 500, 1000, 2000, 3000, 5000)
    val varianceValues: List<Int> = listOf(20, 40, 60)
    val failureValues: List<Int> = listOf(0, 1, 3, 10, 25, 50, 75, 100)
    val errorPercentValues: List<Int> = listOf(0, 1, 3, 10, 25, 50, 75, 100)
    val errorCodeValues: List<Int> = listOf(400, 401, 403, 500, 501, 503, 504)
}
