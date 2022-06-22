package effective.band.compose.drawer_base

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

internal class ShakeDetector(
    private val planet: Float = SensorManager.GRAVITY_EARTH,
) : SensorEventListener {

    var listener: (() -> Unit)? = null

    private var shakeTimestamp: Long = 0

    fun setShakeGestureSensitivity(sensitivity: Float) {
        shakeThresholdGravity = sensitivity
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (listener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            val gX = x / planet
            val gY = y / planet
            val gZ = z / planet

            // gForce will be close to 1 when there is no movement
            val gForce = sqrt((gX * gX + gY * gY + gZ * gZ).toDouble())
                .toFloat()
            if (gForce > shakeThresholdGravity) {
                val now = System.currentTimeMillis()
                // ignore shake events too close to each other (500ms)
                if (shakeTimestamp + SHAKE_SPACING_TIME_MS > now) {
                    return
                }
                shakeTimestamp = now
                listener?.invoke()
            }
        }
    }

    companion object {
        private var shakeThresholdGravity = 3.0f
        private const val SHAKE_SPACING_TIME_MS = 500
    }
}