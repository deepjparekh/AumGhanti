package org.deepparekh.aumghanti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AumGhanti(modifier: Modifier = Modifier) {
    MaterialTheme {
        Box(
            modifier = modifier
                .fillMaxSize()
                // todo: make this honor system theme
                .background(color = Color.White)
        ) {
            Image(
                // todo: remove white background from image asset
                painter = painterResource(id = R.drawable.ghanti_image),
                contentDescription = "Aum ghanti image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 60.dp,
                        vertical = 144.dp
                    )
            )
        }

    }
}