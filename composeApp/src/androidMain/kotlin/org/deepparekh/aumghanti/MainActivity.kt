package org.deepparekh.aumghanti

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: GhantiSensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = GhantiSensorManager(this)
        lifecycle.addObserver(sensorManager)

        setContent {
            App()
        }

        sensorManager.shakeEvent
            .onEach { handleShake() }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun handleShake() {
        Log.d("MainActivity", "handleShake")
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}