package org.deepparekh.aumghanti

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.GRAVITY_EARTH
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

// Reference : https://github.com/DevLucem/Shake-Detector/blob/master/shakedetector/src/main/java/com/lucem/anb/shakedetector/ShakeDetector.java
class GhantiSensorManager(
    private val context: Context,
): SensorEventListener, DefaultLifecycleObserver {

    private companion object {
        // In "Gs" (one Earth gravity unit)
        const val SHAKE_THRESHOLD = 5f
        const val MIN_TIME_BETWEEN_SHAKES_MILLIS = 300
        const val TAG = "GhantiSensorManager"
    }

    private val _shakeEvent = MutableSharedFlow<Unit>()
    val shakeEvent = _shakeEvent.asSharedFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var previousShakeTimeStamp = 0L
    private var sensorManager: SensorManager? = null
    private var accelerometerSensor: Sensor? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d(TAG, "onCreate")
        sensorManager = context.getSystemService(SENSOR_SERVICE) as? SensorManager
        accelerometerSensor = sensorManager?.getDefaultSensor(TYPE_ACCELEROMETER)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d(TAG, "onStart")
        accelerometerSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d(TAG, "onStop")
        sensorManager?.unregisterListener(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d(TAG, "onDestroy")
        accelerometerSensor = null
        sensorManager = null
        try {
            coroutineScope.cancel()
        } catch (exception: Exception) {
            Log.e(TAG, "caught exception $exception while canceling coroutineScope")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (
            event.sensor.type == TYPE_ACCELEROMETER &&
            (event.timestamp - previousShakeTimeStamp) > MIN_TIME_BETWEEN_SHAKES_MILLIS
        ) {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration = sqrt(x.pow(2) +y.pow(2) + z.pow(2)) - GRAVITY_EARTH

            if (acceleration > SHAKE_THRESHOLD) {
                previousShakeTimeStamp = event.timestamp
                coroutineScope.launch { _shakeEvent.emit(Unit) }
            }
        } else {
            Log.d(TAG, "ignoring sensor reading for ${event.sensor.name}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // no-op
    }
}