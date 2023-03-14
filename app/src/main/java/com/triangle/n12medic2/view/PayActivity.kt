package com.triangle.n12medic2.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
<<<<<<< HEAD
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
=======
import androidx.compose.animation.AnimatedVisibility
>>>>>>> Session-51
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
<<<<<<< HEAD
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
=======
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
>>>>>>> Session-51
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
<<<<<<< HEAD
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppOutlinedButton
import com.triangle.n12medic2.ui.theme.captionColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Активити Заказ успешно оплачен
// Дата создания: 11.03.2023 16:17
=======
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppOutlinedButton
import com.triangle.n12medic2.ui.components.LoadingIndicator
import kotlinx.coroutines.delay

// Активити оформления оплаты заказа
// Дата создания: 14.03.2023 11:41
>>>>>>> Session-51
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

<<<<<<< HEAD
    // Экран Заказ успешно оплачен
    // Дата создания: 11.03.2023 16:17
=======
    // Экран оформления оплаты заказа
    // Дата создания: 14.03.2023 11:41
>>>>>>> Session-51
    // Автор: Triangle
    @Composable
    fun PayScreen() {
        val mContext = LocalContext.current
        var isLoading by rememberSaveable { mutableStateOf(true) }

        LaunchedEffect(Unit) {
<<<<<<< HEAD
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
=======
            delay(1000)
            isLoading = false
        }

        val analysisText = buildAnnotatedString {
            append("Не забудьте ознакомиться с ")

            pushStringAnnotation("link", annotation = "https://medic.madskill.ru/avatar/prav.pdf")
            withStyle(style = SpanStyle(MaterialTheme.colors.primary)) {
                append("правилами подготовки к сдаче анализов")
            }
            pop()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Оплата",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            AnimatedVisibility(visible = isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoadingIndicator()
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(text = "Связываемся с банком...", color = captionColor, fontSize = 16.sp)
                }
            }
            AnimatedVisibility(visible = !isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .height(250.dp),
                        painter = painterResource(id = R.drawable.onboard_1),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Ваш заказ успешно оплачен!",
                        color = Color(0xFF00B712),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Вам осталось дождаться приезда медсестры и сдать анализы. До скорой встречи!",
                        color = captionColor,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ClickableText(
                        text = analysisText,
                        style = TextStyle(
                            color = captionColor,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        ),
                        onClick = { offset ->
                            analysisText.getStringAnnotations("link", offset, offset).firstOrNull().let {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://medic.madskill.ru/avatar/prav.pdf")))
                            }
                        }
                    )
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = !isLoading) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        AppOutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth(),
>>>>>>> Session-51
                            label = "Чек покупки",
                            onClick = {

                            },
<<<<<<< HEAD
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
=======
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colors.primary,
                                backgroundColor = Color.White
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        AppButton(
                            modifier = Modifier
                                .fillMaxWidth(),
>>>>>>> Session-51
                            label = "На главную",
                            onClick = {
                                val hIntent = Intent(mContext, HomeActivity::class.java)
                                startActivity(hIntent)
<<<<<<< HEAD
                            },
                            fontSize = 15.sp,
                            modifier = Modifier.fillMaxWidth()
=======
                            }
>>>>>>> Session-51
                        )
                    }
                }
            }
        }
    }
<<<<<<< HEAD

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
=======
>>>>>>> Session-51
}