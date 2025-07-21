package org.deepparekh.aumghanti.di

import org.deepparekh.aumghanti.GhantiViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    viewModelOf(::GhantiViewModel)
}