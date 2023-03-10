package com.triangle.n12medic2.view.screens

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
import androidx.compose.ui.Modifier
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
import com.triangle.n12medic2.viewmodel.AnalyzesViewModel
import kotlinx.coroutines.launch

// Экран Анализы/Главная
// Дата создания: 09.03.2023 09:05
// Автор: Triangle
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnalyzesScreen(
    viewModel: AnalyzesViewModel
) {
    val mContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    var searchValue by rememberSaveable { mutableStateOf("") }
    var isNewsVisible by rememberSaveable { mutableStateOf(true) }
    var isErrorVisible by rememberSaveable { mutableStateOf(false) }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isSearchEnabled by rememberSaveable { mutableStateOf(false) }

    val  searchInteractionSource = remember { MutableInteractionSource() }
    if (searchInteractionSource.collectIsPressedAsState().value) {
        isSearchEnabled = true
    }

    val errorMessage by viewModel.message.observeAsState()
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            isErrorVisible = true
        }
    }

    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }
    var selectedAnalysis by remember { mutableStateOf(Analysis(0,"", "", "", "", "", "", "")) }

    val cart: MutableList<CartItem> = remember { mutableStateListOf() }
    LaunchedEffect(Unit) {
        cart.addAll(CartService().loadCart(sharedPreferences))
    }
    
    LaunchedEffect(Unit) {
        viewModel.loadNews()
        viewModel.loadCatalog()
        isLoading = true
    }

    val isSuccessLoadNews by viewModel.isSuccessLoadNews.observeAsState()
    val isSuccessLoadCatalog by viewModel.isSuccessLoadNews.observeAsState()
    LaunchedEffect(isSuccessLoadNews) {
        if (isSuccessLoadNews == true && isSuccessLoadCatalog == true) {
            isLoading = false
        }
    }
    LaunchedEffect(isSuccessLoadCatalog) {
        if (isSuccessLoadCatalog == true && isSuccessLoadNews == true) {
            isLoading = false
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset }.collect() {
            isNewsVisible = it <= 0
        }
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    ModalBottomSheetLayout(
        sheetContent = {
            var isInCart = false
            for (item in cart) {
                if (item.id == selectedAnalysis.id) {
                    isInCart = true
                    break
                }
            }
            AnalyzesBottomSheet(
                analysis = selectedAnalysis,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onAddClick = { analysis ->
                    if (isInCart) {
                        val index = cart.indexOfFirst { it.id == analysis.id }

                        cart.removeAt(index)
                    } else {
                        cart.add(CartItem(analysis.id, analysis.name, analysis.price, 1))
                    }
                    CartService().saveCart(sharedPreferences, cart)
                },
                isInCart = isInCart
            )
        },
        sheetState = sheetState,
        sheetShape = MaterialTheme.shapes.large.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp))
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.loadNews()
                viewModel.loadCatalog()
            }
        ) {
            Box() {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppTextField(
                            modifier = Modifier
                                .fillMaxWidth(if (isSearchEnabled) 0.75f else 1f),
                            value = searchValue,
                            onValueChange = { searchValue = it },
                            placeholder = {
                                Text(
                                    text = "Искать анализы",
                                    fontSize = 16.sp,
                                    color = captionColor
                                )
                            },
                            interactionSource = searchInteractionSource,
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search),
                                    contentDescription = "",
                                    tint = descriptionColor
                                )
                            },
                            trailingIcon = {
                                AnimatedVisibility(visible = isSearchEnabled) {
                                   IconButton(onClick = {
                                       searchValue = ""
                                   }) {
                                       Icon(
                                           modifier = Modifier
                                               .size(12.dp),
                                           painter = painterResource(id = R.drawable.ic_close),
                                           contentDescription = "",
                                           tint = descriptionColor,
                                       )
                                   }
                                }
                            }
                        )
                        AnimatedVisibility(visible = isSearchEnabled) {
                            AppTextButton(
                                label = "Отменить",
                                onClick = {
                                    isSearchEnabled = false
                                    searchValue = ""
                                },
                                textStyle = TextStyle(
                                    fontSize = 14.sp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colors.primary,
                                    backgroundColor = Color.White
                                )
                            )
                        }
                    }
                    AnimatedVisibility(visible = !isSearchEnabled) {
                        Column( modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                AnimatedVisibility(visible = isNewsVisible) {
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())) {
                                        BlockTitle(title = "Акции и новости")
                                        Spacer(modifier = Modifier.height(16.dp))
                                        LazyRow() {
                                            items(viewModel.news.distinct()) { news ->
                                                NewsComponent(news = news)
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                BlockTitle(title = "Каталог анализов")
                                Spacer(modifier = Modifier.height(16.dp))
                                LazyRow() {
                                    items(viewModel.categories) { cat ->
                                        CategoryChip(
                                            name = cat,
                                            selected = selectedCategory == cat,
                                            onClick = { selectedCategory = it }
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                LazyColumn(
                                    state = lazyListState
                                ) {
                                    item {
                                        Spacer(modifier = Modifier.height(18.dp))
                                    }
                                    items(viewModel.analysis.filter { it.category.lowercase() == selectedCategory.lowercase() }.distinct()) { analysis ->
                                        var isInCart = false
                                        for (item in cart) {
                                            if (item.id == analysis.id) {
                                                isInCart = true
                                                break
                                            }
                                        }

                                        AnalysisComponent(
                                            analysis = analysis,
                                            onClick = {
                                                selectedAnalysis = analysis
                                                scope.launch {
                                                    sheetState.show()
                                                }
                                            },
                                            onAddClick = {
                                                if (isInCart) {
                                                    val index = cart.indexOfFirst { it.id == analysis.id }

                                                    cart.removeAt(index)
                                                } else {
                                                    cart.add(CartItem(analysis.id, analysis.name, analysis.price, 1))
                                                }

                                                CartService().saveCart(sharedPreferences, cart)
                                            },
                                            isInCart = isInCart
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                }
                            }
                        }
                    }
                    AnimatedVisibility(visible = isSearchEnabled) {
                        LazyColumn() {
                            items(items = viewModel.analysis.filter { if (searchValue.length > 2) it.name.lowercase().contains(searchValue.lowercase()) else false }) { analysis ->
                                AnalysisSearchComponent(
                                    analysis = analysis,
                                    onClick = {
                                        selectedAnalysis = analysis

                                        scope.launch { sheetState.show() }
                                    },
                                    searchValue = searchValue
                                )
                            }
                        }
                    }
                }
                if (cart.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(20.dp, 24.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Log.d(TAG, "AnalyzesScreen: ${cart.size}")
                        AppCartButton(
                            cart = cart,
                            onClick = {
                                val intent = Intent(mContext, CartActivity::class.java)
                                mContext.startActivity(intent)
                            }
                        )
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
}

@Composable
private fun BlockTitle(
    title: String
) {
    Text(
        text = title,
        fontSize = 17.sp,
        color = captionColor,
        fontWeight = FontWeight.SemiBold
    )
}