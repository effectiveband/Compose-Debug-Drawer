package band.effective.drawer_location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class LocationModuleViewModel(private val context: Context) {

    private val scope = CoroutineScope(Dispatchers.Main)
    private val mutableState = MutableStateFlow<Location?>(null)
    val state = mutableState.asStateFlow()

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest = LocationRequest.create().apply {
        interval = 100
        fastestInterval = 50
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 100
    }

    suspend fun loadLocation() = scope.async {
        mutableState.value = null
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.e("PackageManager.PERMISSION_GRANTED")
            fusedLocationClient.lastLocation.addOnFailureListener {
                Timber.e(it)
            }
            fusedLocationClient.locationAvailability.addOnSuccessListener {
                if (it.isLocationAvailable) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        mutableState.value = location
                    }
                } else {
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(result: LocationResult) {
                                mutableState.value = result.lastLocation
                            }
                        },
                        Looper.getMainLooper()
                    );
                }
            }
        } else {
            Timber.e("PackageManager.PERMISSION_DENIED")
        }
    }.await()
}