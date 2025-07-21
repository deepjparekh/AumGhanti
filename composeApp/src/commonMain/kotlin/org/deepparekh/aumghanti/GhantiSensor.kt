package org.deepparekh.aumghanti

import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

interface GhantiSensor {
    val shakeEvent: Flow<Unit>
}

expect val ghantiSensorModule: Module