package org.deepparekh.aumghanti

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private companion object {
        const val TAG = "MainActivity"
        const val BELL_PLAYING_TIMEOUT_MILLIS = 300L
        const val BELL_IDLE_TIMEOUT_MILLIS = 2000L
    }

    private lateinit var sensorManager: GhantiSensorManager
    private var mediaPlayer: MediaPlayer? = null
    private var bellPlayingJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = GhantiSensorManager(this)
        mediaPlayer = MediaPlayer.create(this, R.raw.bell)
        lifecycle.addObserver(sensorManager)

        setContent {
            App()
        }

        sensorManager.shakeEvent
            .onEach { handleShake() }
            .launchIn(lifecycle.coroutineScope)
    }

    override fun onStop() {
        super.onStop()
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) {
            Log.e(TAG, "caught exception $e while stopping play")
        }
        clearBellPlayingJob()
    }

    private fun handleShake() {
        mediaPlayer?.let { player ->
            if (player.isPlaying.not()) {
                Log.d(TAG, "handleShake: start playing bell")
                player.start()
                reAttachBellPlayingJob(BELL_IDLE_TIMEOUT_MILLIS)
            } else {
                Log.d(TAG, "handleShake: ignore because bell is already being played")
                reAttachBellPlayingJob(BELL_PLAYING_TIMEOUT_MILLIS)
            }
        } ?: Log.e(TAG, "handleShake: media player null")
    }

    private fun reAttachBellPlayingJob(delay: Long) {
        clearBellPlayingJob()
        bellPlayingJob = lifecycle.coroutineScope.launch {
            delay(delay)
            try {
                Log.d(TAG, "stop playing bell after timeout")
                mediaPlayer?.stop()
                Log.d(TAG, "re-preparing player for next play")
                mediaPlayer?.prepare()
            } catch (exception: Exception) {
                Log.e(TAG, "reAttachBellPlayingJob caught exception $exception")
            }
        }
    }

    private fun clearBellPlayingJob() {
        bellPlayingJob?.cancel()
        bellPlayingJob = null
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}