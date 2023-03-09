package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultStrokeLineMiter
import androidx.compose.ui.unit.dp
import com.triangle.n12medic2.ui.theme.iconsColor

// Компонент нижнего меню навигации
// Дата создания: 09.03.2023 08:51
// Автор: Triangle
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    BottomNavigation(
        modifier = modifier
            .drawBehind {
                drawLine(
                    iconsColor,
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    DefaultStrokeLineMiter
                )
            },
        backgroundColor = Color.White,
        contentColor = iconsColor,
        elevation = 0.dp,
        content = content
    )
}