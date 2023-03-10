package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.ui.theme.iconsColor

// Компонет товара в корзине
// Дата создания: 10.03.2023 09:54
// Автор: Triangle
@Composable
fun CartComponent(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onPlus: () -> Unit,
    onMinus: () -> Unit,
) {
    Box(
        modifier = Modifier
            .shadow(10.dp, MaterialTheme.shapes.medium, spotColor = Color.Black.copy(0.2f))
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(140.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    text = cartItem.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier
                        .size(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        tint = captionColor,
                        modifier = Modifier
                            .size(12.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${cartItem.price} ₽",
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${cartItem.count} пациент",
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CartButtons(
                        onPlusClick = onPlus,
                        onMinusClick = onMinus,
                        minusEnabled = cartItem.count > 1,
                        plusEnabled = true
                    )
                }
            }
        }
    }
}