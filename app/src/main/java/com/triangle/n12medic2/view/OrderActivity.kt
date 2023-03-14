package com.triangle.n12medic2.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.common.OrderService
import com.triangle.n12medic2.common.UserService
import com.triangle.n12medic2.model.*
import com.triangle.n12medic2.ui.components.*
import com.triangle.n12medic2.ui.theme.*
import com.triangle.n12medic2.viewmodel.OrderViewModel
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
        val viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val scope = rememberCoroutineScope()
        val mContext = LocalContext.current
        val sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

        val token = sharedPreferences.getString("token", "")

        var isErrorVisible by rememberSaveable { mutableStateOf(false) }
        var isLoading by rememberSaveable { mutableStateOf(false) }

        val isSuccess by viewModel.isSuccess.observeAsState()
        LaunchedEffect(isSuccess) {
            if (isSuccess == true) {
                val intent = Intent(mContext, PayActivity::class.java)
                startActivity(intent)
            }
        }

        val errorMessage by viewModel.message.observeAsState()
        LaunchedEffect(errorMessage) {
            if (errorMessage != null) {
                isLoading = false
                isErrorVisible = true
            }
        }

        var selectedScreen by rememberSaveable { mutableStateOf("address") }

        var addressValue by rememberSaveable { mutableStateOf("") }
        var timeValue by rememberSaveable { mutableStateOf("") }
        var phoneValue by rememberSaveable { mutableStateOf("") }
        var commentValue by rememberSaveable { mutableStateOf("") }
        
        var selectedPatientIndex = 0
        
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

        val cart: MutableList<CartItem> = remember { mutableStateListOf() }
        val users: MutableList<User> = remember { mutableStateListOf() }
        val selectedUsers: MutableList<User> = remember { mutableStateListOf() }
        val sUsers: MutableList<User> = remember { mutableStateListOf() }

        LaunchedEffect(Unit) {
            cart.addAll(CartService().loadCart(sharedPreferences))
            users.addAll(UserService().loadPatients(sharedPreferences))

            if (users.isNotEmpty()) {
                selectedUsers.add(User(
                    users[0].firstName,
                    users[0].lastname,
                    users[0].patronymic,
                    users[0].birthday,
                    users[0].gender,
                    users[0].image,
                    cart
                ))
            }

            val address = OrderService().loadAddress(sharedPreferences)
            if (address != null) {
                addressValue = address.address
            }
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
                        PatientSelectBottomSheet(
                            onPatientChange = {
                                if (selectedPatientIndex == -1) {
                                    selectedUsers.add(it)
                                } else {
                                    selectedUsers[selectedPatientIndex] = it
                                }

                                sUsers.clear()
                                sUsers.addAll(selectedUsers)
                                selectedUsers.clear()
                                selectedUsers.addAll(sUsers)
                            },
                            onCloseClick = { scope.launch { sheetState.hide() } },
                            userList = users
                        )
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
                            icon = painterResource(id = R.drawable.ic_back),
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
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                    ) {
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
                            Text(
                                text = buildAnnotatedString {
                                    append("Кто будет сдавать анализы?")
                                    withStyle(style = SpanStyle(color = MaterialTheme.colors.error)) {
                                        append(" *")
                                    }
                                },
                                fontSize = 14.sp,
                                color = descriptionColor
                            )
                            if (selectedUsers.size == 1) {
                                AppTextField(
                                    value = selectedUsers[0].lastname + " " + selectedUsers[0].firstName,
                                    onValueChange = {},
                                    readOnly = true,
                                    leadingIcon = {
                                        Image(
                                            painter = if(selectedUsers[0].gender == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    selectedScreen = "patient"
                                                    selectedPatientIndex = 0
                                                    sheetState.show()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_expand),
                                                contentDescription = "",
                                                tint = descriptionColor
                                            )
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            } else if (selectedUsers.size > 1) {
                                for ((i, u) in selectedUsers.withIndex()) {
                                    MultipleUserCard(
                                        user = u,
                                        onUserClick = {
                                            selectedPatientIndex = i
                                            scope.launch {
                                                sheetState.show()
                                            }
                                        },
                                        onRemoveClick = { usr ->
                                            selectedUsers.remove(usr)
                                        },
                                        onCartChange = { tcart ->
                                            selectedUsers[i].cart = tcart

                                            sUsers.clear()
                                            sUsers.addAll(selectedUsers)
                                            selectedUsers.clear()
                                            selectedUsers.addAll(sUsers)
                                        },
                                        cart = cart
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }

                            AppOutlinedButton(
                                label = "Добавить еще пациента",
                                onClick = {
                                   scope.launch {
                                       selectedScreen = "patient"
                                       selectedPatientIndex = -1
                                       sheetState.show()
                                   }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = MaterialTheme.colors.primary
                                ),
                                textStyle = TextStyle(
                                    fontWeight = FontWeight.Normal
                                ),
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            AppTextField(
                                value = phoneValue,
                                onValueChange = {
                                    phoneValue = it
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
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                                var count = 0
                                var sum = 0

                                for (u in selectedUsers.distinct()) {
                                    for (c in u.cart.distinct()) {
                                        count += 1
                                        sum += c.price.toInt()
                                    }
                                }

                                Text(
                                    text = "$count анализ",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                Text(
                                    text = "$sum ₽",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            AppButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Заказать",
                                onClick = {
                                    var selectedPatients = ArrayList<OrderPatient>()

                                    for (u in selectedUsers) {
                                        var orderCart = ArrayList<OrderItems>()
                                        for (c in u.cart) {
                                            orderCart.add(OrderItems(c.id, c.price))
                                        }
                                        selectedPatients.add(OrderPatient(
                                            u.lastname + " " + u.firstName,
                                            orderCart
                                        ))
                                    }

                                     val order = Order(
                                         addressValue,
                                         timeValue,
                                         phoneValue,
                                         commentValue,
                                         "",
                                         selectedPatients
                                     )

                                    if (token != null) {
                                        isLoading = true
                                        viewModel.sendOrder(order, token)
                                    }
                                },
                                enabled = addressValue.isNotEmpty() && timeValue.isNotEmpty() && phoneValue.isNotEmpty()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
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