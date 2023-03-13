package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.ui.components.AppIconButton
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.iconsColor

// Активити Оформление заказа/1 пациент
// Дата создания: 10.03.2023 11:14
// Автор: Triangle
class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OrderScreen()
                }
            }
        }
    }

    // Экран оформление заказа
    // Дата создания: 13.03.2023 11:37
    // Автор: Triangle
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun OrderScreen() {
        val mContext = LocalContext.current
        var selectedScreen by rememberSaveable { mutableStateOf("address") }
        
        ModalBottomSheetLayout(
            sheetContent = {
                when (selectedScreen) {
                    "address" -> {

                    }
                    "time" -> {

                    }
                    "patient" -> {

                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)) {
                        AppIconButton(
                            modifier = Modifier
                                .padding(top = 20.dp),
                            icon = painterResource(id = com.triangle.n12medic2.R.drawable.ic_back),
                            size = 40.dp
                        ) {
                            val intent = Intent(mContext, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Оформление заказа",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            ) {
                Box(Modifier.padding(it)) {
                    
                }
            }
        }
    }
}