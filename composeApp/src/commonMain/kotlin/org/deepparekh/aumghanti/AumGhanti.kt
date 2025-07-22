package org.deepparekh.aumghanti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import aumghanti.composeapp.generated.resources.Res
import aumghanti.composeapp.generated.resources.ghanti
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AumGhanti(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<GhantiViewModel>()
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color.Black
    } else Color.White
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Image(
            painter = painterResource(Res.drawable.ghanti),
            contentDescription = "Aum ghanti image",
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 32.dp,
                    vertical = 40.dp
                )
        )
    }
}