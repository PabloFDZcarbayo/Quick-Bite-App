package com.example.quickbite.View.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShadowBackground() { // Sombra en gradiente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, // Transparente en la parte superior
                        Color.Black.copy(alpha = 0.5f) // Negro semitransparente en la parte inferior
                    ), startY = 0f, // Comienza en la parte superior
                    endY = Float.POSITIVE_INFINITY // Termina en la parte inferior
                )
            )
    )
}