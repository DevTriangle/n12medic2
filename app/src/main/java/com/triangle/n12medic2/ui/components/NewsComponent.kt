package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.triangle.n12medic2.model.News

// Компонент новстей на главном экране
// Дата создания: 09.03.2023 09:14
// Автор: Triangle
@Composable
fun NewsComponent(
    news: News
) {
    Card(
        modifier = Modifier
            .padding(end = 16.dp),
        elevation = 0.dp,
        backgroundColor = Color(0xFF76B3FF)
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .width(300.dp),
        ) {
            GlideImage(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                imageModel = news.image,
                imageOptions = ImageOptions(
                    alignment = Alignment.BottomEnd,
                    contentScale = ContentScale.FillHeight
                ),
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = news.name,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Column() {
                    Text(
                        text = news.description,
                        fontSize = 10.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${news.price} ₽",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}