package org.deepparekh.aumghanti

import androidx.compose.ui.window.ComposeUIViewController
import org.deepparekh.aumghanti.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}