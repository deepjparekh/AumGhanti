package org.deepparekh.aumghanti.di

import org.deepparekh.aumghanti.ghantiMediaPlayerModule
import org.deepparekh.aumghanti.ghantiSensorModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = startKoin {
        appDeclaration()
        modules(ghantiSensorModule, ghantiMediaPlayerModule, appModule)
    }