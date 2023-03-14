package com.triangle.n12medic2.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.common.OrderService
import com.triangle.n12medic2.common.UserService
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.components.*
import com.triangle.n12medic2.ui.theme.*
import kotlinx.coroutines.launch

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
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun OrderScreen() {
        val scope = rememberCoroutineScope()
        val mContext = LocalContext.current
        val sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        var selectedScreen by rememberSaveable { mutableStateOf("address") }

        var addressValue by rememberSaveable { mutableStateOf("") }
        var timeValue by rememberSaveable { mutableStateOf("") }
        var phoneValue by rememberSaveable { mutableStateOf("") }
        val address = OrderService().loadAddress(sharedPreferences)
        var commentValue by rememberSaveable { mutableStateOf("") }

        val addressInteraction = remember { MutableInteractionSource() }
        if (addressInteraction.collectIsPressedAsState().value) {
            selectedScreen = "address"
            scope.launch { sheetState.show() }
        }

        val timeInteraction = remember { MutableInteractionSource() }
        if (timeInteraction.collectIsPressedAsState().value) {
            selectedScreen = "time"
            scope.launch { sheetState.show() }
        }

        val users: MutableList<User> = remember { mutableStateListOf() }
        val selectedUsers: MutableList<User> = remember { mutableStateListOf() }

        LaunchedEffect(Unit) {
            users.addAll(UserService().loadPatients(sharedPreferences))
            if (users.isNotEmpty()) {
                selectedUsers.add(users[0])
            }
        }

        if (address != null) {
            addressValue = address.address
        }

        ModalBottomSheetLayout(
            sheetContent = {
                when (selectedScreen) {
                    "address" -> {
                        AddressBottomSheet(onAddressChange = {
                            addressValue = it
                        })
                    }
                    "time" -> {
                        TimeSelectBottomSheet(
                            onTimeChange = {
                                timeValue = it
                            }
                        ) {
                            scope.launch { sheetState.hide() }
                        }
                    }
                    "patient" -> {

                    }
                }
            },
            sheetState = sheetState
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
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            AppTextField(
                                value = addressValue,
                                onValueChange = {
                                    addressValue = it
                                },
                                readOnly = true,
                                interactionSource = addressInteraction,
                                label = {
                                    Text(
                                        text = "Адрес *",
                                        fontSize = 14.sp,
                                        color = descriptionColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "Введите ваш адрес",
                                        fontSize = 15.sp,
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            AppTextField(
                                value = timeValue,
                                onValueChange = {
                                    timeValue = it
                                },
                                interactionSource = timeInteraction,
                                readOnly = true,
                                label = {
                                    Text(
                                        text = "Дата и время*",
                                        fontSize = 14.sp,
                                        color = descriptionColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "Выберите дату и время",
                                        fontSize = 15.sp,
                                        color = captionColor
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            if ()
                            Spacer(modifier = Modifier.height(32.dp))
                            AppTextField(
                                value = phoneValue,
                                onValueChange = {
                                    addressValue = it
                                },
                                label = {
                                    Text(
                                        text = "Телеофн *",
                                        fontSize = 14.sp,
                                        color = descriptionColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "+7 (967) 078-58-37",
                                        fontSize = 15.sp,
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "Комментарий",
                                        fontSize = 14.sp,
                                        color = descriptionColor
                                    )
                                    IconButton(
                                        onClick = { /*TODO*/ },
                                        modifier = Modifier
                                            .size(20.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_mic),
                                            contentDescription = "",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                AppTextField(
                                    value = commentValue,
                                    onValueChange = { commentValue = it },
                                    modifier = Modifier.height(128.dp),
                                    placeholder = {
                                        Text(
                                            text = "Можете оставить свои пожелания",
                                            fontSize = 15.sp,
                                            color = captionColor
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(40.dp))
                            }
                        }
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .background(inputBG)
                            .padding(20.dp, 16.dp)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "Оплата Apple Pay",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                IconButton(onClick = {}, Modifier.size(20.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right),
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp),
                                        tint = iconsColor
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "Промокод",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = captionColor
                                )
                                IconButton(onClick = {}, Modifier.size(20.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right),
                                        contentDescription = "",
                                        modifier = Modifier.size(16.dp),
                                        tint = iconsColor
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(29.dp))
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "count анализ",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text = "sum ₽",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            AppButton(
                                label = "Заказать",
                                onClick = {

                                },
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}