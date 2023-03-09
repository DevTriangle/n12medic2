package com.triangle.n12medic2.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.*
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.viewmodel.AnalyzesViewModel
import kotlinx.coroutines.flow.collect

// Экран Анализы/Главная
// Дата создания: 09.03.2023 09:05
// Автор: Triangle
@Composable
fun AnalyzesScreen(
    viewModel: AnalyzesViewModel
) {
    val lazyListState = rememberLazyListState()

    var searchValue by rememberSaveable { mutableStateOf("") }
    var isNewsVisible by rememberSaveable { mutableStateOf(true) }
    var isErrorVisible by rememberSaveable { mutableStateOf(false) }

    var isLoading by rememberSaveable { mutableStateOf(false) }

    val errorMessage by viewModel.message.observeAsState()
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            isErrorVisible = true
        }
    }

    var selectedCategory by rememberSaveable { mutableStateOf("Популярные") }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchValue,
                onValueChange = { searchValue = it },
                placeholder = {
                    Text(
                        text = "Искать анализы",
                        fontSize = 16.sp,
                        color = captionColor
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        tint = descriptionColor
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AnimatedVisibility(visible = isNewsVisible) {
                Column(modifier = Modifier.fillMaxWidth()) {
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
                    AnalysisComponent(
                        analysis = analysis,
                        onClick = {

                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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