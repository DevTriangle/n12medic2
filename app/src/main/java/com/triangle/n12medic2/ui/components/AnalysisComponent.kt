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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultStrokeLineMiter
import androidx.compose.ui.graphics.vector.DefaultStrokeLineWidth
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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

// Компонент анализа в поиске
// Дата создания: 10.03.2023 11:51
// Автор: Triangle
@Composable
fun AnalysisSearchComponent(
    analysis: Analysis,
    onClick: (Analysis) -> Unit,
    searchValue: String
) {
    val sText = analysis.name.split(searchValue)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(analysis) }
            .drawBehind {
                drawLine(
                    Color(0xFFF4F4F4),
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    DefaultStrokeLineMiter
                )
            }
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                text = buildAnnotatedString {
                    for ((i, t) in sText.withIndex()) {
                        append(t)
                        if (i != sText.size - 1) {
                            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                                append(searchValue)
                            }
                        }
                    }
                },
                fontSize = 15.sp
            )
            Column() {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "${analysis.price} ₽",
                    textAlign = TextAlign.End,
                    fontSize = 17.sp
                    )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = analysis.time_result,
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    color = captionColor
                )
            }
        }
    }
}