package com.triangle.n12medic2.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.theme.*

// Элемент пациента при выборе
// Дата создания: 11.03.2023 15:14
// Автор: Хасанов Альберт
@Composable
fun PatientCard(
    user: User,
    selected: Boolean,
    onSelect: (User) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onSelect(user) },
        elevation = 0.dp,
        backgroundColor = if (selected) MaterialTheme.colors.primary else inputBG
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {
            Image(
                painter = if(user.gender == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "${user.lastname} ${user.firstName}",
                fontSize = 16.sp,
            )
        }
    }
}

// Карточка пациента при оформлении заказа
// Дата создания: 11.03.2023 15:37
// Автор: Хасанов Альберт
@SuppressLint("MutableCollectionMutableState")
@Composable
fun PatientMultipleCard(
    user: User,
    interactionSource: MutableInteractionSource,
    onRemove: (User) -> Unit,
    cart: MutableList<CartItem>,
    onUserCartChange: (MutableList<CartItem>) -> Unit
) {
    var tCart: MutableList<CartItem> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {
        tCart.addAll(cart)
    }

    Card(
        modifier = Modifier
            .border(1.dp, inputStroke, MaterialTheme.shapes.medium),
        elevation = 0.dp,
        backgroundColor = Color.White,
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    value = "${user.lastname} ${user.firstName}",
                    onValueChange = {  },
                    readOnly = true,
                    singleLine = true,
                    interactionSource = interactionSource,
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_expand),
                            contentDescription = "",
                            tint = descriptionColor
                        )
                    },
                    leadingIcon = {
                        Image(
                            painter = if(user.gender == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                )
                IconButton(
                    onClick = { onRemove(user) },
                    modifier = Modifier.size(18.dp).fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        tint = iconsColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            for (c in cart) {
                var isChecked by rememberSaveable { mutableStateOf(true) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.7f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    if (isChecked) tCart.remove(c)
                                    else tCart.add(c)

                                    isChecked = it

                                    onUserCartChange(tCart)
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colors.primary
                                )
                            )
                            Text(
                                text = c.name,
                                fontSize = 12.sp,
                            )
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "${c.price} ₽",
                            fontSize = 15.sp,
                        )
                    }
            }
        }
    }
}