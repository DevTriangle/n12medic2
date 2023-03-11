package com.triangle.n12medic2.view

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
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.triangle.n12medic2.view.CartActivity
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.ui.components.*
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.ui.theme.iconsColor
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

                }
            }
        }
    }

    // Экран оформления заказа
    // Дата создания: 11.03.2023 13:41
    // Автор: Хасанов Альберт
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun OrderScreen() {
        val mContext = LocalContext.current
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
        val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

        val selectedSheet by rememberSaveable { mutableStateOf("address") }

        val addressValue by rememberSaveable { mutableStateOf("") }
        val timeValue by rememberSaveable { mutableStateOf("") }
        val phoneValue by rememberSaveable { mutableStateOf("") }
        val commentValue by rememberSaveable { mutableStateOf("") }

        val addressInteractionSource = remember { MutableInteractionSource() }.collectIsPressedAsState()
        LaunchedEffect(addressInteractionSource) {
            if (addressInteractionSource.value) {
                sheetState.show()
            }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                when(selectedSheet) {
                    "address" -> {
                        Text(text = "")
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
                Box(modifier = Modifier.padding(it)) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                        ) {
                            AppTextField(
                                value = addressValue,
                                onValueChange = { addressValue = it },
                                readOnly = true,
                                interactionSource =
                            )
                        }
                        Column {

                        }
                    }
                }
            }
        }
    }
}