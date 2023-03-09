package com.triangle.n12medic2.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.triangle.n12medic2.R

// Экран результатов
// Дата создания: 09.03.2023 10:07
// Автор: Triangle
@Composable
fun ResultsScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_app_foreground),
            contentDescription = ""
        )
    }
}