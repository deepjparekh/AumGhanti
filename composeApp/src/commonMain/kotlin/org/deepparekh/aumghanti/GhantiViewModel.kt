package org.deepparekh.aumghanti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GhantiViewModel(
    ghantiSensor: GhantiSensor,
    private val ghantiMediaPlayer: GhantiMediaPlayer,
): ViewModel() {

    private companion object {
        const val BELL_PLAYING_TIMEOUT_MILLIS = 300L
        const val BELL_IDLE_TIMEOUT_MILLIS = 1500L
    }

    private var bellPlayingJob: Job? = null

    init {
        ghantiSensor.shakeEvent
            .onEach { handleShake() }
            .launchIn(viewModelScope)
    }

    private fun handleShake() {
        Logger.d { "handleShake" }
        if (ghantiMediaPlayer.isPlaying.not()) {
            Logger.d { "ghantiMediaPlayer start" }
            ghantiMediaPlayer.start()
            reAttachBellPlayingJob(BELL_IDLE_TIMEOUT_MILLIS)
        } else {
            Logger.d { "ghantiMediaPlayer continue" }
            reAttachBellPlayingJob(BELL_PLAYING_TIMEOUT_MILLIS)
        }
    }

    private fun reAttachBellPlayingJob(delay: Long) {
        bellPlayingJob?.cancel()
        bellPlayingJob = viewModelScope.launch {
            delay(delay)
            stopAndPreparePlayer()
        }
    }

    private fun stopAndPreparePlayer() {
        try {
            Logger.d { "ghantiMediaPlayer stopAndPreparePlayer" }
            ghantiMediaPlayer.stop()
            ghantiMediaPlayer.prepare()
        } catch (exception: Exception) {
            Logger.e { "stopAndPreparePlayer error $exception" }
        }
    }
}