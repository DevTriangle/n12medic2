package com.triangle.n12medic2.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.triangle.n12medic2.R

// Индикация загрузки
// Дата создания: 14.03.2023 12:13
// Автор: Triangle
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    val rotation = remember {
        Animatable(0f)
    }

    LaunchedEffect(rotation) {
        rotation.animateTo(
            360f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Image(
        painter = painterResource(id = R.drawable.ic_load),
        contentDescription = "",
        modifier = modifier
            .size(66.dp)
            .rotate(rotation.value)
        )
}