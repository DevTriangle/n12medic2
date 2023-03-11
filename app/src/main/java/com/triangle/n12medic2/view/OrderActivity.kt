package com.triangle.n12medic2.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.triangle.n12medic2.view.CartActivity
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.common.UserService
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.components.*
import com.triangle.n12medic2.ui.theme.*
import com.triangle.n12medic2.viewmodel.AnalyzesViewModel
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

    // Экран оформления заказа
    // Дата создания: 11.03.2023 13:41
    // Автор: Хасанов Альберт
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun OrderScreen() {
        val mContext = LocalContext.current
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
        val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

        var selectedSheet by rememberSaveable { mutableStateOf("address") }

        var addressValue by rememberSaveable { mutableStateOf("") }
        val timeValue by rememberSaveable { mutableStateOf("") }
        val phoneValue by rememberSaveable { mutableStateOf("") }
        var commentValue by rememberSaveable { mutableStateOf("") }

        var cart: MutableList<CartItem> = remember { mutableStateListOf() }
        LaunchedEffect(Unit) {
            cart.addAll(CartService().loadCart(sharedPreferences))
        }

        var selectedUsers: MutableList<User> = remember { mutableStateListOf() }
        val userList: MutableList<User> = remember { mutableStateListOf() }
        LaunchedEffect(Unit) {
            userList.addAll(UserService().loadPatients(sharedPreferences))

            if (userList.isNotEmpty()) {
                selectedUsers.add(0, User(
                    userList[0].firstName,
                    userList[0].lastname,
                    userList[0].patronymic,
                    userList[0].birthday,
                    userList[0].gender,
                    userList[0].image,
                    cart
                ))
            }
        }

        var selectedPatientIndex = 0

        val addressInteractionSource = remember { MutableInteractionSource() }
        if (addressInteractionSource.collectIsPressedAsState().value) {
            selectedSheet = "address"
            scope.launch { sheetState.show() }
        }

        val timeInteractionSource = remember { MutableInteractionSource() }
        if (timeInteractionSource.collectIsPressedAsState().value) {
            selectedSheet = "time"
            scope.launch { sheetState.show() }
        }

        var sUsers: MutableList<User> = remember {
            mutableStateListOf()
        }

        ModalBottomSheetLayout(
            sheetContent = {
                when(selectedSheet) {
                    "address" -> {
                        AddressBottomSheet {
                            addressValue = it.address
                        }
                    }
                    "time" -> {
                        AddressBottomSheet {
                            addressValue = it.address
                        }
                    }
                    "patient" -> {
                        PatientSelectBottomSheet(
                            patientOnChange = {
                                if (selectedPatientIndex == -1) {
                                    selectedUsers.add(User(
                                        it.firstName,
                                        it.lastname,
                                        it.patronymic,
                                        it.birthday,
                                        it.gender,
                                        it.image,
                                        cart
                                    ))
                                } else {
                                    selectedUsers[selectedPatientIndex] = it

                                    sUsers.clear()
                                    sUsers.addAll(selectedUsers)
                                    selectedUsers.clear()
                                    selectedUsers.addAll(sUsers)
                                }
                            },
                            patientList = userList,
                            onDismiss = {
                                scope.launch { sheetState.hide() }
                                        },
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
                            val intent = Intent(mContext, CartActivity::class.java)
                            startActivity(intent)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Оформление заказа",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            ) {
                Box(modifier = Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                        ) {
                            AppTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = addressValue,
                                onValueChange = { addressValue = it },
                                readOnly = true,
                                singleLine = true,
                                interactionSource = addressInteractionSource,
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
                                },
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            AppTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = addressValue,
                                onValueChange = { addressValue = it },
                                readOnly = true,
                                singleLine = true,
                                interactionSource = timeInteractionSource,
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
                                },
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            if (selectedUsers.isNotEmpty()) {
                                if (selectedUsers.size == 1) {
                                    val patientInteractionSource = remember { MutableInteractionSource() }
                                    if (patientInteractionSource.collectIsPressedAsState().value) {
                                        selectedSheet = "patient"
                                        selectedPatientIndex = 0
                                        scope.launch { sheetState.show() }
                                    }

                                    AppTextField(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        value = selectedUsers[0].lastname + " " + selectedUsers[0].firstName,
                                        onValueChange = { addressValue = it },
                                        readOnly = true,
                                        singleLine = true,
                                        interactionSource = patientInteractionSource,
                                        label = {
                                            Text(
                                                text = buildAnnotatedString {
                                                    append("Кто будет сдавать анализы?")
                                                    withStyle(style = SpanStyle(color = errorColor)) {
                                                        append(" *")
                                                    }
                                                },
                                                fontSize = 14.sp,
                                                color = descriptionColor
                                            )
                                        },
                                        placeholder = {
                                            Text(
                                                text = "${selectedUsers[0].lastname} ${selectedUsers[0].firstName}",
                                                fontSize = 15.sp,
                                            )
                                        },
                                        trailingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_expand),
                                                contentDescription = "",
                                                tint = descriptionColor
                                            )
                                        },
                                        leadingIcon = {
                                            Image(
                                                painter = if(selectedUsers[0].gender == "Мужской") painterResource(id = R.drawable.ic_male) else painterResource(id = R.drawable.ic_female),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(24.dp)
                                            )
                                        }
                                    )
                                } else {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        for ((i,u) in selectedUsers.withIndex()) {
                                            val patientInteractionSource = remember { MutableInteractionSource() }
                                            if (patientInteractionSource.collectIsPressedAsState().value) {
                                                selectedSheet = "patient"
                                                selectedPatientIndex = i
                                                scope.launch { sheetState.show() }
                                            }
                                            PatientMultipleCard(
                                                user = u,
                                                interactionSource = patientInteractionSource,
                                                onRemove = {
                                                    selectedUsers.remove(it)
                                                },
                                                cart = cart,
                                                onUserCartChange = {
                                                    selectedUsers[i].cart = it
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            AppOutlinedButton(
                                label = "Добавить еще пациента",
                                onClick = {
                                    scope.launch {
                                        selectedSheet = "patient"
                                        selectedPatientIndex = -1
                                        sheetState.show()
                                    }
                                },
                                fontSize = 15.sp,
                                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = MaterialTheme.colors.primary
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            AppTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = addressValue,
                                onValueChange = { addressValue = it },
                                readOnly = true,
                                singleLine = true,
                                interactionSource = addressInteractionSource,
                                label = {
                                    Text(
                                        text = "Телефон *",
                                        fontSize = 14.sp,
                                        color = descriptionColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "+7 (967) 078-58-37",
                                        fontSize = 15.sp,
                                    )
                                },
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Комментарий",
                                        fontSize = 14.sp,
                                        color = descriptionColor,
                                    )
                                    IconButton(
                                        onClick = { /*TODO*/ },
                                        modifier = Modifier
                                            .size(20.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_mic),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(18.dp)
                                        )
                                    }
                                }
                                AppTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(128.dp),
                                    value = commentValue,
                                    onValueChange = { commentValue = it },
                                    readOnly = true,
                                    placeholder = {
                                        Text(
                                            text = "Можете оставить свои пожелания",
                                            fontSize = 15.sp,
                                        )
                                    },
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(inputBG)
                                .padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Оплата Apple Pay",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(20.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right),
                                        contentDescription = "",
                                        tint = iconsColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Промокод",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(20.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right),
                                        contentDescription = "",
                                        tint = iconsColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(29.dp))
                            AppButton(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Заказать",
                                onClick = {
//                                        val intent = Intent(mContext, PayActivity::class.java)
//                                        startActivity(intent)
                                },
                                enabled = addressValue != "" && selectedUsers.isNotEmpty() && phoneValue != "" && timeValue != ""
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}