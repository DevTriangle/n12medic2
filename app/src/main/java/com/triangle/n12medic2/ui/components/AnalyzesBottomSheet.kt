package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.ui.theme.captionColor

@Composable
fun AnalyzesBottomSheet(
    analysis: Analysis,
    onDismiss: () -> Unit,
    onAddClick: (Analysis) -> Unit,
    isInCart: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    text = analysis.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                AppIconButton(
                    icon = painterResource(id = R.drawable.ic_close),
                    onClick = onDismiss,
                    shape = CircleShape,
                    size = 26.dp
                )
            }
            Text(
                text = "Описание",
                color = captionColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = analysis.description,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Подготовка",
                color = captionColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = analysis.preparation,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(
                        text = "Результаты через:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = captionColor
                    )
                    Text(
                        text = analysis.time_result,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    )
                }
                Column(modifier = Modifier.fillMaxWidth(1f)) {
                    Text(
                        text = "Биоматериал:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = captionColor
                    )
                    Text(
                        text = analysis.bio,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            AppButton(
                label = if (isInCart) "Убрать" else "Добавить за ${analysis.price} ₽",
                onClick = { onAddClick(analysis) },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}