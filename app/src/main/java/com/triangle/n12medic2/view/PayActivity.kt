package com.triangle.n12medic2.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppOutlinedButton
import com.triangle.n12medic2.ui.theme.captionColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Активити Заказ успешно оплачен
// Дата создания: 11.03.2023 16:17
// Автор: Triangle
class PayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PayScreen()
                }
            }
        }
    }

    // Экран Заказ успешно оплачен
    // Дата создания: 11.03.2023 16:17
    // Автор: Triangle
    @Composable
    fun PayScreen() {
        val mContext = LocalContext.current
        var isLoading by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(3000)
            isLoading = false
        }

        Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Оплата",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = isLoading) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        LoadingIndicator()
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Связываемся с банком...",
                            fontSize = 16.sp,
                            color = captionColor
                        )
                    }
                }
                AnimatedVisibility(visible = !isLoading) {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.onboard_1),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Text(
                            text = "Ваш заказ успешно оплачен!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF00B712)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Вам осталось дождаться приезда медсестры и сдать анализы. До скорой встречи!",
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val aString = buildAnnotatedString {
                            append("Не забудьте ознакомиться с ")
                            pushStringAnnotation("rules", annotation = "https://medic.madskill.ru/avatar/prav.pdf")

                            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                                append("правилами подготовки к сдаче анализов")
                            }
                            pop()
                        }
                        ClickableText(
                            text = aString,
                            style = TextStyle(
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            ),
                            onClick = { offset ->
                                aString.getStringAnnotations(tag = "rules", start = offset, end = offset).firstOrNull()?.let {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://medic.madskill.ru/avatar/prav.pdf")))
                                }
                            }
                        )
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVisibility(visible = !isLoading) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        AppOutlinedButton(
                            label = "Чек покупки",
                            onClick = {

                            },
                            fontSize = 15.sp,
                            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                                contentColor = MaterialTheme.colors.primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        AppButton(
                            label = "На главную",
                            onClick = {
                                val hIntent = Intent(mContext, HomeActivity::class.java)
                                startActivity(hIntent)
                            },
                            fontSize = 15.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    // Индикатор загрузки
    // Дата создания: 11.03.2023 16:18
    // Автор: Triangle
    @Composable
    private fun LoadingIndicator() {
        val rotation = remember {
            androidx.compose.animation.core.Animatable(0f)
        }
        
        LaunchedEffect(rotation) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
            )
        }
        
        Image(
            painter = painterResource(id = R.drawable.ic_loading),
            contentDescription = "",
            modifier = Modifier
                .rotate(rotation.value)
        )
    }
}