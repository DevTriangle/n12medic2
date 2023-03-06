package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Компонент индикации номера страницы
@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    count: Int = 3,
    currentIndex: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until count) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(if (currentIndex == i) MaterialTheme.colors.primaryVariant else Color.White)
                    .border(1.dp, MaterialTheme.colors.primaryVariant, CircleShape)
            )
        }
    }
}