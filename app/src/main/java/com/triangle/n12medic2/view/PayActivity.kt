package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppOutlinedButton
import com.triangle.n12medic2.ui.components.LoadingIndicator

// Активити оформления оплаты заказа
// Дата создания: 14.03.2023 11:41
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

    // Экран оформления оплаты заказа
    // Дата создания: 14.03.2023 11:41
    // Автор: Triangle
    @Composable
    fun PayScreen() {
        val mContext = LocalContext.current
        var isLoading by rememberSaveable { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Оплата")
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LoadingIndicator()
                Spacer(modifier = Modifier.height(26.dp))
                Text(text = "Связываемся с банком...", color = captionColor, fontSize = 16.sp)
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = !isLoading) {
                   AppOutlinedButton(
                       label = "Чек покупки",
                       onClick = { /*TODO*/ },
                       colors = ButtonDefaults.buttonColors(
                           contentColor = MaterialTheme.colors.primary,
                           backgroundColor = Color.White
                       ),
                       border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                   )
                    Spacer(modifier = Modifier.height(20.dp))
                    AppButton(
                        label = "На главную",
                        onClick = {
                            val hIntent = Intent(mContext, HomeActivity::class.java)
                            startActivity(hIntent)
                        }
                    )
                }
            }
        }
    }
}