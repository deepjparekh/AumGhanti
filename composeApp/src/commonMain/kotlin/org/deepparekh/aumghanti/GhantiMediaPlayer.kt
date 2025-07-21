package org.deepparekh.aumghanti

import org.koin.core.module.Module

interface GhantiMediaPlayer {

    fun prepare()
    fun start()
    fun pause()
    fun stop()
    val isPlaying: Boolean
}

expect val ghantiMediaPlayerModule: Module