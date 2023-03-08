package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.PasswordManager
import com.triangle.n12medic2.ui.components.*
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.captionColor

class CreatePasswordActivity : ComponentActivity() {
    // Экран создания пароля
    // Дата создания: 08.03.2023 13:49
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreatePasswordScreen()
                }
            }
        }
    }

    // Содержание экрана создания пароля
    // Дата создания: 08.03.2023 13:49
    // Автор: Triangle
    @Composable
    fun CreatePasswordScreen() {
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)
        val mContext = LocalContext.current

        var password by rememberSaveable { mutableStateOf("") }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    AppTextButton(
                        label = "Пропустить",
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colors.primary,
                            backgroundColor = Color.White
                        ),
                        onClick = {
                            with(sharedPreferences.edit()) {
                                putBoolean("isPasswordSkipped", true)
                                apply()
                            }

                            val cardIntent = Intent(mContext, ManageCardActivity::class.java)
                            startActivity(cardIntent)
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
                        .padding(horizontal = 50.dp, vertical = 40.dp)
                        .widthIn(max = 400.dp)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Создайте пароль",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Для защиты ваших персональных данных",
                            fontSize = 15.sp,
                            color = captionColor,
                            textAlign = TextAlign.Center
                        )
                    }
                    PasswordDotsIndicator(currentCount = password.length)
                    LazyVerticalGrid(
                        modifier = Modifier
                            .height(392.dp)
                            .width(288.dp),
                        columns = GridCells.Fixed(3),
                        userScrollEnabled = false,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        content = {
                            items(
                                count = 12
                            ) { index ->
                                if (index < 9) {
                                    AppPasswordButton(
                                        label = (index + 1).toString(),
                                        onClick = {
                                            if (password.length < 3) {
                                                password = "${password}$it"
                                            } else if (password.length == 3) {
                                                password = "${password}$it"

                                                PasswordManager().savePassword(sharedPreferences, password)
                                                val cardIntent = Intent(mContext, ManageCardActivity::class.java)
                                                startActivity(cardIntent)
                                            }
                                        }
                                    )
                                } else if (index == 10) {
                                    AppPasswordButton(
                                        label = "0",
                                        onClick = {
                                            if (password.length < 3) {
                                                password = "${password}$it"
                                            } else if (password.length == 3) {
                                                password = "${password}$it"

                                                PasswordManager().savePassword(sharedPreferences, password)
                                                val cardIntent = Intent(mContext, ManageCardActivity::class.java)
                                                startActivity(cardIntent)
                                            }
                                        }
                                    )
                                } else if (index == 11) {
                                    IconButton(
                                        modifier = Modifier
                                            .size(80.dp),
                                        onClick = {
                                            if (password.isNotEmpty()) {
                                                password = password.substring(0, password.length - 1)
                                            }
                                        }
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_del),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}