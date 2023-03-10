package com.triangle.n12medic2.view.screens

import android.content.ContentValues
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.skydoves.landscapist.glide.GlideImage
import com.triangle.n12medic2.R
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppTextField
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.viewmodel.ManageCardViewModel

// Экран "Профиль"
// Дата создания: 09.03.2023 10:08
// Автор: Triangle
@Composable
fun ProfileScreen(
    viewModel: ManageCardViewModel
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", ComponentActivity.MODE_PRIVATE)

    val token = sharedPreferences.getString("token", "")

    var firstName by rememberSaveable { mutableStateOf("") }
    var patronymic by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isErrorVisible by rememberSaveable { mutableStateOf(false) }

    val userList: MutableList<User> = remember { mutableStateListOf() }

    LaunchedEffect(Unit) {

    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Карта пациента",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(7.dp))
            GlideImage(
                imageModel =
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Без карты пациента вы не сможете заказать анализы.",
                fontSize = 14.sp,
                color = captionColor,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
                fontSize = 14.sp,
                color = captionColor,
            )
            Spacer(modifier = Modifier.height(32.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Имя",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = firstName,
                onValueChange = {
                    firstName = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Отчество",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = patronymic,
                onValueChange = {
                    patronymic = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Фамилия",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = lastName,
                onValueChange = {
                    lastName = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Дата рождения",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = birthday,
                onValueChange = {
                    birthday = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = isExpanded,
                onExpandedChange = {
                    isExpanded = it
                }
            ) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Пол",
                            color = captionColor,
                            fontSize = 15.sp
                        )
                    },
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = { isExpanded = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_expand),
                                contentDescription = ""
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = { gender = "Мужской" }
                    ) {
                        Text(text = "Мужской")
                    }
                    DropdownMenuItem(
                        onClick = { gender = "Женский" }
                    ) {
                        Text(text = "Женский")
                    }
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Создать",
                enabled = firstName.isNotBlank() && patronymic.isNotBlank() && lastName.isNotBlank() && birthday.isNotBlank() && gender.isNotBlank(),
                onClick = {
                    Log.d(ContentValues.TAG, "ManageCardScreen: $token")
                    viewModel.createProfile(firstName, patronymic, lastName, birthday, gender, token!!)
                    isLoading = true
                }
            )
        }
    }
}