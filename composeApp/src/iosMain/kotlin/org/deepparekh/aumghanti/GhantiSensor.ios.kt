package org.deepparekh.aumghanti

import co.touchlab.kermit.Logger
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.CoreMotion.CMAcceleration
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue
import kotlin.math.pow
import kotlin.math.sqrt

class GhantiSensorImpl : GhantiSensor {
    private companion object {
        const val ACCELEROMETER_UPDATE_INTERVAL = 0.2
        const val SHAKE_THRESHOLD = 2f
        const val SHAKE_UPDATE_DEBOUNCE_MILLIS = 100L
    }

    private val motionManager: CMMotionManager by lazy { CMMotionManager() }
    private val mainQueue: NSOperationQueue by lazy { NSOperationQueue.mainQueue }


    @OptIn(ExperimentalForeignApi::class, FlowPreview::class)
    override val shakeEvent: Flow<Unit> = callbackFlow {
        Logger.d { "shakeEvent flow started" }
        motionManager.setAccelerometerUpdateInterval(ACCELEROMETER_UPDATE_INTERVAL)
        motionManager.startAccelerometerUpdatesToQueue(mainQueue) { data, error ->
            val accelerationData: CValue<CMAcceleration>? = data?.acceleration
            if (error == null && accelerationData != null) {
                accelerationData.useContents {
                    val acceleration = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
                    if (acceleration > SHAKE_THRESHOLD) {
                        trySend(Unit)
                    }
                }
            }
        }
        awaitClose {
            motionManager.stopAccelerometerUpdates()
        }
    }
         .debounce(timeoutMillis = SHAKE_UPDATE_DEBOUNCE_MILLIS)
}

actual val ghantiSensorModule: Module = module {
    single<GhantiSensor> { GhantiSensorImpl() }
}