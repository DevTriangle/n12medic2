package com.triangle.n12medic2.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.theme.*
import kotlinx.coroutines.launch

// Карточка выбора пациента
// Дата создания: 14.03.2023 10:35
// Автор: Triangle
@Composable
fun UserCard(
    user: User,
    selected: Boolean,
    onSelect: (User) -> Unit
) {
    Card(
        elevation = 0.dp,
        backgroundColor = if (selected) MaterialTheme.colors.primary else inputBG,
        modifier = Modifier
            .clickable {
                onSelect(user)
            }
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Image(
                painter = if(user.gender == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = user.lastname + " " + user.firstName,
                fontSize = 16.sp
            )
        }
    }
}

// Карточка при отображении нескольких пациентов
// Дата создания: 14.03.2023 10:35
// Автор: Triangle
@Composable
fun MultipleUserCard(
    user: User,
    onUserClick: () -> Unit,
    onRemoveClick: (User) -> Unit,
    onCartChange: (MutableList<CartItem>) -> Unit,
    cart: MutableList<CartItem>
) {
    var tCart: MutableList<CartItem> = remember {
        mutableStateListOf()
    }

    LaunchedEffect(Unit) {
        tCart.addAll(cart)
    }
    
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .border(1.dp, inputStroke, MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 24.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    value = user.lastname + " " + user.firstName,
                    onValueChange = {},
                    readOnly = true,
                    leadingIcon = {
                        Image(
                            painter = if(user.gender == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = onUserClick
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_expand),
                                contentDescription = "",
                                tint = descriptionColor
                            )
                        }
                    }
                )
                IconButton(
                    onClick = { onRemoveClick(user) },
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        tint = iconsColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            for ((index, c) in cart.withIndex()) {
                var isChecked by rememberSaveable { mutableStateOf(true) }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = it

                                if (isChecked) tCart.add(c)
                                else tCart.remove(c)

                                Log.d(TAG, "MultipleUserCard: ${tCart.size}")

                                onCartChange(tCart)
                            },
                            colors =CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primary,
                                uncheckedColor = captionColor
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(0.7f),
                            text = c.name,
                            fontSize = 12.sp,
                            color = if (isChecked) Color.Black else captionColor
                        )
                    }
                    Text(
                        text = "${c.price} ₽",
                        fontSize = 15.sp,
                        color = if (isChecked) Color.Black else captionColor
                    )
                }
            }
        }
    }
}