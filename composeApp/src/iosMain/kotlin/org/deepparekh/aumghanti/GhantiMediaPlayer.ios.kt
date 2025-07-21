package org.deepparekh.aumghanti

import aumghanti.composeapp.generated.resources.Res
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

class GhantiMediaPlayerImpl : GhantiMediaPlayer {
    @OptIn(ExperimentalForeignApi::class)
    private val player: AVAudioPlayer? by lazy {
        val uriString = Res.getUri("files/bell.mp3")
        NSURL.URLWithString(uriString)?.let { it ->
            AVAudioPlayer(contentsOfURL = it, error = null)
        }
    }

    override fun prepare() {
        player?.prepareToPlay()
    }

    override fun start() {
        // keep playing until stopped.
        player?.numberOfLoops = -1
        player?.play()
    }

    override fun pause() {
        player?.pause()
    }

    override fun stop() {
        player?.stop()
    }

    override val isPlaying: Boolean = player?.isPlaying() ?: false
}

actual val ghantiMediaPlayerModule: Module = module {
    singleOf(::GhantiMediaPlayerImpl) { bind<GhantiMediaPlayer>() }
}