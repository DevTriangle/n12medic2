package com.triangle.n12medic2.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.common.UserService
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppTextButton
import com.triangle.n12medic2.ui.components.AppTextField
import com.triangle.n12medic2.ui.components.LoadingDialog
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.viewmodel.ManageCardViewModel

// Активити создания карты пациента
// Дата создания: 08.03.2023 14:47
// Автор: Triangle
class ManageCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ManageCardScreen()
                }
            }
        }
    }

    // Экран создания карты пациента
    // Дата создания: 08.03.2023 14:47
    // Автор: Triangle
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ManageCardScreen() {
        val mContext = LocalContext.current
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)
        val viewModel = ViewModelProvider(this)[ManageCardViewModel::class.java]

        val addNew = sharedPreferences.getBoolean("addNew", false)

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
            userList.addAll(UserService().loadPatients(sharedPreferences))
        }

        val isSuccess by viewModel.isSuccess.observeAsState()
        LaunchedEffect(isSuccess) {
            if (isSuccess == true) {
                isLoading = false

                val intent = Intent(mContext, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        val errorMessage by viewModel.errorMessage.observeAsState()
        LaunchedEffect(isErrorVisible) {
            if (errorMessage != null) {
                isErrorVisible = true
                isLoading = false
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        text = "Создание карты пациента",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    AppTextButton(
                        label = "Пропустить",
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.Transparent
                        ),
                        onClick = {
                            val homeIntent = Intent(mContext, HomeActivity::class.java)
                            startActivity(homeIntent)
                        }
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
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
                            viewModel.createProfile(firstName, patronymic, lastName, birthday, gender, token!!)
                            isLoading = true

                            if (addNew) {
                                userList.add(User(firstName, lastName, patronymic, birthday, gender, ""))
                            } else {
                                userList.add(User(firstName, lastName, patronymic, birthday, gender, ""))
                            }

                            UserService().savePatients(sharedPreferences, userList)
                        }
                    )
                }
            }
        }

        if (isErrorVisible) {
            AlertDialog(
                title = { Text(
                    text = "Ошибка",
                    fontSize = 18.sp
                ) },
                text = { Text(
                    text = errorMessage.toString(),
                    fontSize = 15.sp
                ) },
                onDismissRequest = {isErrorVisible = false},
                confirmButton = {
                    AppTextButton(
                        label = "Ок",
                        onClick = { isErrorVisible = false }
                    )
                }
            )
        }

        if (isLoading) {
            LoadingDialog()
        }
    }
}