package org.deepparekh.aumghanti.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.koin.KermitKoinLogger
import org.deepparekh.aumghanti.ghantiMediaPlayerModule
import org.deepparekh.aumghanti.ghantiSensorModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
    logger(KermitKoinLogger(Logger.withTag("koin")))
    appDeclaration()
    modules(ghantiSensorModule, ghantiMediaPlayerModule, appModule)
}