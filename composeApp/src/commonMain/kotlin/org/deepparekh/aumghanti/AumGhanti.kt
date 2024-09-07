package org.deepparekh.aumghanti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aumghanti.composeapp.generated.resources.Res
import aumghanti.composeapp.generated.resources.ghanti_image
import org.jetbrains.compose.resources.painterResource

@Composable
fun AumGhanti(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            // todo: make this honor system theme
            .background(color = Color.White)
    ) {
        Image(
            // todo: remove white background from image asset
            painter = painterResource(Res.drawable.ghanti_image),
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