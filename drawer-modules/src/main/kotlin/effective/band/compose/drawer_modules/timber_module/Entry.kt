package effective.band.compose.drawer_modules.timber_module

import android.util.Log
import androidx.compose.ui.graphics.Color

class Entry(val level: Int, val tag: String?, val message: String, val date: String) {
    // Indent newlines to match the original indentation.
    fun prettyPrint() = "%s %s %s".format(
        tag, displayLevel(),
        message.replace(
            "\\n".toRegex(),
            "\n"
        )
    )

    fun displayLevel() = when (level) {
        Log.VERBOSE -> "V"
        Log.DEBUG -> "D"
        Log.INFO -> "I"
        Log.WARN -> "W"
        Log.ERROR -> "E"
        Log.ASSERT -> "A"
        else -> "?"
    }

    fun displayColor() = when (level) {
        Log.VERBOSE -> Color.Blue
        Log.DEBUG -> Color.Blue
        Log.INFO -> Color.Green
        Log.WARN -> Color(0xFFFFBF00)
        Log.ERROR -> Color.Red
        Log.ASSERT -> Color.Red
        else -> Color.LightGray
    }
}