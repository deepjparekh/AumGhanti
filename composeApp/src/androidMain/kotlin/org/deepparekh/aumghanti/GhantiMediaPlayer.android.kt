package org.deepparekh.aumghanti

import android.content.Context
import android.media.MediaPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

class GhantiMediaPlayerImpl(
    private val context: Context,
): GhantiMediaPlayer {

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer.create(
            context,
            R.raw.bell
        )
    }


    override fun prepare() {
        mediaPlayer.prepare()
    }

    override val isPlaying: Boolean = mediaPlayer.isPlaying

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun stop() {
        mediaPlayer.stop()
    }
}

actual val ghantiMediaPlayerModule: Module = module {
    single<GhantiMediaPlayer> { GhantiMediaPlayerImpl(androidContext()) }
}