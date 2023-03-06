package com.triangle.n12medic2.ui.components

import android.app.ActivityManager.TaskDescription
import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.ui.theme.captionColor

// Компонент приветственного экрана, содержащего заголовок, описание и изобаржение.
@Composable
fun OnboardComponent(
    title: String,
    description: String,
    image: Painter,
    currentIndex: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color(0xFF00B712),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(29.dp))
            Text(
                text = description,
                color = captionColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
        DotsIndicator(currentIndex = currentIndex)
        Image(
            modifier = Modifier
                .height(250.dp),
            painter = image,
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )
    }
}