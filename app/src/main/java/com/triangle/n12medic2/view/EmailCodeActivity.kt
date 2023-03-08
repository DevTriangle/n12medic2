package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import android.widget.Space
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppCodeField
import com.triangle.n12medic2.ui.components.AppIconButton
import com.triangle.n12medic2.ui.components.AppTextButton
import com.triangle.n12medic2.ui.components.LoadingDialog
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.viewmodel.AuthViewModel
import com.triangle.n12medic2.viewmodel.EmailCodeViewModel
import kotlinx.coroutines.delay

class EmailCodeActivity : ComponentActivity() {
    // Экран ввода кода из email
    // Дата создания: 08.03.2023 12:59
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    EmailCodeScreen()
                }
            }
        }
    }

    // Содержание экрана ввода кода из email
    // Дата создания: 08.03.2023 12:59
    // Автор: Triangle
    @Composable
    fun EmailCodeScreen() {
        val mContext = LocalContext.current
        val focus = LocalFocusManager.current
        val viewModel = ViewModelProvider(this)[EmailCodeViewModel::class.java]
        val authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)

        var code1 by rememberSaveable { mutableStateOf("") }
        var code2 by rememberSaveable { mutableStateOf("") }
        var code3 by rememberSaveable { mutableStateOf("") }
        var code4 by rememberSaveable { mutableStateOf("") }

        var sec by rememberSaveable { mutableStateOf(60) }

        var email by rememberSaveable { mutableStateOf(intent.getStringExtra("email")) }
        var isLoading by rememberSaveable { mutableStateOf(false) }
        var isErrorVisible by rememberSaveable { mutableStateOf(false) }

        val token by viewModel.token.observeAsState()
        LaunchedEffect(token) {
            if (token != null) {
                with(sharedPreferences.edit()) {
                    putString("token", token)
                    apply()
                }

                isLoading = false

                val passwordIntent = Intent(mContext, CreatePasswordActivity::class.java)
                startActivity(passwordIntent)
            }
        }

        val errorMessage by viewModel.errorMessage.observeAsState()
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                isLoading = false
                isErrorVisible = true
            }
        }

        LaunchedEffect(sec) {
            if (sec > 1) {
                delay(1000)
                sec--
            } else if (sec == 0) {
                authViewModel.sendCode(email!!)
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            topBar = {
                AppIconButton(
                    icon = painterResource(id = R.drawable.ic_back)
                ) {
                    onBackPressed()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = 400.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Введите код из E-mail",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AppCodeField(
                            value = code1,
                            onValueChange = {
                                if (it.length <= 1 && it.isDigitsOnly()) {
                                    code1 = it
                                    focus.moveFocus(FocusDirection.Next)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppCodeField(
                            value = code2,
                            onValueChange = {
                                if (it.length <= 1 && it.isDigitsOnly()) {
                                    code2 = it
                                    focus.moveFocus(FocusDirection.Next)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppCodeField(
                            value = code3,
                            onValueChange = {
                                if (it.length <= 1 && it.isDigitsOnly()) {
                                    code3 = it
                                    focus.moveFocus(FocusDirection.Next)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AppCodeField(
                            value = code4,
                            onValueChange = {
                                if (it.length <= 1 && it.isDigitsOnly()) {
                                    code4 = it

                                    if (code1.isNotBlank() && code2.isNotBlank() && code3.isNotBlank() && code4.isNotBlank()) {
                                        viewModel.signIn(email!!, "${code1}${code2}${code3}${code4}")
                                        isLoading = true
                                    }

                                    focus.clearFocus()
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Отправить код повторно можно будет через $sec секунд",
                        fontSize = 15.sp,
                        color = captionColor
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