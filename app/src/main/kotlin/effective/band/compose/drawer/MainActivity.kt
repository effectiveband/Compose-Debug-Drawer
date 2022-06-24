package effective.band.compose.drawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import effective.band.compose.drawer.ui.ComposeDrawerTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Debug log")
        Timber.e("Error log")
        Timber.w("Warning log")
        Timber.i("Info log")
        Timber.v("Verbose log")

        setContent {
            ComposeDrawerTheme {
                HomeScreen()
            }
        }
    }
}