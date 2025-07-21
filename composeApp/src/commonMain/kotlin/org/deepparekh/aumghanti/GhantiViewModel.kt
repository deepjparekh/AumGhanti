package org.deepparekh.aumghanti

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        const val BELL_IDLE_TIMEOUT_MILLIS = 1000L
    }

    private var bellPlayingJob: Job? = null

    init {
        ghantiSensor.shakeEvent
            .onEach { handleShake() }
            .launchIn(viewModelScope)
    }

    private fun handleShake() {
        if (ghantiMediaPlayer.isPlaying.not()) {
            ghantiMediaPlayer.start()
            reAttachBellPlayingJob(BELL_IDLE_TIMEOUT_MILLIS)
        } else {
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
            ghantiMediaPlayer.stop()
            ghantiMediaPlayer.prepare()
        } catch (exception: Exception) {

        }
    }
}