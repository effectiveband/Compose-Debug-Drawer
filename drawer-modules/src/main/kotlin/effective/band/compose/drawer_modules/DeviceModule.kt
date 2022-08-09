package effective.band.compose.drawer_modules

import android.os.Build
import android.util.DisplayMetrics
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceUnknown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import band.effective.drawer_base.InfoModule

@Composable
fun DeviceModule(modifier: Modifier = Modifier) {

    fun getDensityString(displayMetrics: DisplayMetrics): String {
        return when (displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> "ldpi"
            DisplayMetrics.DENSITY_MEDIUM -> "mdpi"
            DisplayMetrics.DENSITY_HIGH -> "hdpi"
            DisplayMetrics.DENSITY_XHIGH -> "xhdpi"
            DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi"
            DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi"
            DisplayMetrics.DENSITY_TV -> "tvdpi"
            else -> displayMetrics.densityDpi.toString()
        }
    }

    val context = LocalContext.current

    val displayMetrics = context.resources.displayMetrics
    val densityBucket = getDensityString(displayMetrics)

    val deviceMake = "Make" to Build.MANUFACTURER
    val deviceModel = "Device" to Build.MODEL
    val deviceResolution =
        "Resolution" to "${displayMetrics.heightPixels}x${displayMetrics.widthPixels}"
    val deviceDensity = "Density" to "${displayMetrics.densityDpi}dpi ($densityBucket)"
    val deviceRelease = "Release" to Build.VERSION.RELEASE
    val deviceApi = "API" to Build.VERSION.SDK_INT.toString()

    val title = "Device information"

    val items = listOf(
        deviceMake,
        deviceModel,
        deviceResolution,
        deviceDensity,
        deviceRelease,
        deviceApi
    )

    InfoModule(modifier = modifier, icon = {
        Icon(
            imageVector = Icons.Default.DeviceUnknown,
            contentDescription = "Device icon"
        )
    }, title = title, items = items)
}