package org.deepparekh.aumghanti

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.GRAVITY_EARTH
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.math.pow
import kotlin.math.sqrt


class GhantiSensorImpl(
    private val context: Context
): GhantiSensor {

    private companion object {
        // In "Gs" (one Earth gravity unit)
        const val SHAKE_THRESHOLD = 5f
        const val MIN_TIME_BETWEEN_SHAKES_MILLIS = 300
    }

    private val sensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val accelerometerSensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
    }
    private var previousShakeTimeStamp = 0L

    override val shakeEvent: Flow<Unit> = callbackFlow {
        val sensorEventListener: SensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                // no-op
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
                        trySend(Unit)
                    }
                }
            }
        }

        sensorManager.registerListener(
            sensorEventListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_UI,
        )

        awaitClose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }
}

actual val ghantiSensorModule: Module = module {
    single<GhantiSensor> { GhantiSensorImpl(androidContext()) }
}