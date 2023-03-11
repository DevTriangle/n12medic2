package com.triangle.n12medic2.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.ui.theme.iconsColor
import com.triangle.n12medic2.ui.theme.inputStroke

// Нижний экран выбора адреса
// Дата создания: 11.03.2023 13:51
// Автор: Хасанов Альберт
@Composable
fun AddressBottomSheet() {
    var addressText by rememberSaveable { mutableStateOf("") }
    var lonText by rememberSaveable { mutableStateOf("") }
    var latText by rememberSaveable { mutableStateOf("") }
    var altText by rememberSaveable { mutableStateOf("") }

    var flatText by rememberSaveable { mutableStateOf("") }
    var entranceText by rememberSaveable { mutableStateOf("") }
    var floorText by rememberSaveable { mutableStateOf("") }

    var saveAddress by rememberSaveable { mutableStateOf(false) }

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
                    text = "Адрес сдачи анализов",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.ic_map),
                        contentDescription = "",
                        tint = iconsColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                value = addressText,
                onValueChange = { addressText = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = "Ваш адрес",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
                changeBorder = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                AppTextField(
                    value = lonText,
                    onValueChange = { lonText = it },
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .padding(end = 12.5.dp),
                    label = {
                        Text(
                            text = "Долгота",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    changeBorder = false
                )
                AppTextField(
                    value = latText,
                    onValueChange = { latText = it },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(end = 12.5.dp),
                    label = {
                        Text(
                            text = "Широта",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    changeBorder = false
                )
                AppTextField(
                    value = altText,
                    onValueChange = { altText = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "Высота",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    changeBorder = false
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                AppTextField(
                    value = flatText,
                    onValueChange = { flatText = it },
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .padding(end = 12.5.dp),
                    label = {
                        Text(
                            text = "Квартира",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    changeBorder = false
                )
                AppTextField(
                    value = entranceText,
                    onValueChange = { entranceText = it },
                    modifier = Modifier
                        .fillMaxWidth(0.66f)
                        .padding(end = 12.5.dp),
                    label = {
                        Text(
                            text = "Подъезд",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    changeBorder = false
                )
                AppTextField(
                    value = floorText,
                    onValueChange = { floorText = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "Этаж",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    changeBorder = false
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AppTextField(
                value = floorText,
                onValueChange = { floorText = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = "Домофон",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
                changeBorder = false
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Сохранить этот адрес?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Switch(
                    checked = saveAddress,
                    onCheckedChange = { saveAddress = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colors.primary,
                        checkedTrackAlpha = 1f,
                        uncheckedTrackColor = inputStroke,
                        uncheckedTrackAlpha = 1f,
                    )
                )
            }
            AnimatedVisibility(visible = saveAddress) {
                AppTextField(
                    value = floorText,
                    onValueChange = { floorText = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "Домофон",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Название: например дом, работа",
                            fontSize = 15.sp,
                            color = captionColor
                        )
                    },
                    changeBorder = false
                )
            }
        }
    }
}