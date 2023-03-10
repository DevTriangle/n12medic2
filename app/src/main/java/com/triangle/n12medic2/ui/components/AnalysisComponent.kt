package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.ui.theme.captionColor

// Компонент анализа в каталоге
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
@Composable
fun AnalysisComponent(
    analysis: Analysis,
    onClick: () -> Unit,
    isInCart: Boolean,
    onAddClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .shadow(10.dp, MaterialTheme.shapes.medium, spotColor = Color.Black.copy(0.2f))
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                onClick = onClick
            )
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(150.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = analysis.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = analysis.time_result,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = captionColor
                    )
                    Text(
                        text = "${analysis.price} ₽",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (!isInCart) {
                    AppButton(
                        label = "Добавить",
                        onClick = onAddClick,
                        contentPadding = PaddingValues(16.dp, 10.dp),
                        fontSize = 14.sp
                    )
                } else {
                    AppButton(
                        label = "Убрать",
                        onClick = onAddClick,
                        contentPadding = PaddingValues(16.dp, 10.dp),
                        fontSize = 14.sp,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = MaterialTheme.colors.primary
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                    )
                }
            }
        }
    }
}