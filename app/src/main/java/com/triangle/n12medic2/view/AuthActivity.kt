package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppOutlinedButton
import com.triangle.n12medic2.ui.components.AppTextButton
import com.triangle.n12medic2.ui.components.AppTextField
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.viewmodel.AuthViewModel

class AuthActivity : ComponentActivity() {
    // Активити авторизации и регистрации
    // Дата создания: 06.03.2023 15:10
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("authActivity"),
                    color = MaterialTheme.colors.background,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AuthScreen()
                    }
                }
            }
        }
    }

    // Содержание экрана авторизации и регистрации
    // Дата создания: 08.03.2023 12:21
    // Автор: Triangle
    @Composable
    fun AuthScreen() {
        val mContext = LocalContext.current
        val viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        var emailValue by rememberSaveable { mutableStateOf("") }
        var isErrorVisible by rememberSaveable { mutableStateOf(false) }
        var isEmailErrorVisible by rememberSaveable { mutableStateOf(false) }

        val error by viewModel.message.observeAsState()
        LaunchedEffect(error) {
            if (error != null) {
                isErrorVisible = true
            }
        }

        var isLoading by rememberSaveable { mutableStateOf(false) }

        val isSuccess by viewModel.isSuccess.observeAsState()
        LaunchedEffect(isSuccess) {
            if (isSuccess == true) {
                isLoading = false

                val codeIntent = Intent(mContext, EmailCodeActivity::class.java)
                codeIntent.putExtra("email", emailValue)

                startActivity(codeIntent)
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 400.dp)
                .padding(horizontal = 20.dp, vertical = 56.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AuthScreenTitle()
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = emailValue,
                    onValueChange = {
                        emailValue = it
                    },
                    placeholder = {
                        Text(text = "example@mail.ru")
                    },
                    label = {
                        Text(
                            text = "Вход по E-mail",
                            fontSize = 14.sp,
                            color = descriptionColor
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = "Далее",
                    isLoading = isLoading,
                    onClick = {
                        if (Regex("^[a-z0-9]+@([a-z0-9.]+)+[a-z]{2,}$").matches(emailValue)) {
                            viewModel.sendCode(emailValue)
                            isLoading = true
                        } else {
                            isEmailErrorVisible = true
                        }
                    },
                    enabled = emailValue.trim().isNotBlank()
                )
            }
            AuthScreenLoginWith()
        }
        
        if (isErrorVisible) {
            AlertDialog(
                title = { Text(
                    text = "Ошибка",
                    fontSize = 18.sp
                ) },
                text = { Text(
                    text = error.toString(),
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

        if (isEmailErrorVisible) {
            AlertDialog(
                title = { Text(
                    text = "Ошибка",
                    fontSize = 18.sp
                ) },
                text = { Text(
                    text = "Неверный формат Email",
                    fontSize = 15.sp
                ) },
                onDismissRequest = {isEmailErrorVisible = false},
                confirmButton = {
                    AppTextButton(
                        label = "Ок",
                        onClick = { isEmailErrorVisible = false }
                    )
                }
            )
        }
    }

    // Заголовок экрана авторизации и регистрации
    // Дата создания: 08.03.2023 12:21
    // Автор: Triangle
    @Composable
    fun AuthScreenTitle() {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_hello),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Добро пожаловать!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(23.dp))
            Text(
                text = "Войдите, чтобы пользоваться функциями приложения!",
                fontSize = 15.sp,
            )
        }
    }

    // Поле с кнопками "Войти с помощью"
    // Дата создания: 08.03.2023 12:43
    // Автор: Triangle
    @Composable
    fun AuthScreenLoginWith() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Или войдите с помощью",
                fontSize = 15.sp,
            )
            AppOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Войти с Яндекс",
                onClick = {

                }
            )
        }
    }
}