package effective.band.compose.drawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import effective.band.compose.drawer.ui.ComposeDrawerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeDrawerTheme {
                GamesScreen()
            }
        }
    }
}