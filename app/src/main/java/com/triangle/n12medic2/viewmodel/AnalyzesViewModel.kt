package com.triangle.n12medic2.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.model.News
import kotlinx.coroutines.launch

// ViewModel для получения новостей и каталога анализов с сервера
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
class AnalyzesViewModel: ViewModel() {
    var message = MutableLiveData<String>()
    var isSuccessLoadNews = MutableLiveData<Boolean>()
    var isSuccessLoadCatalog = MutableLiveData<Boolean>()

    private var _news:MutableList<News> = mutableStateListOf()
    var news: List<News> by mutableStateOf(_news)

    private var _analysis:MutableList<Analysis> = mutableStateListOf()
    var analysis: List<Analysis> by mutableStateOf(_analysis)

    val categories = listOf(
        "Популярные",
        "Covid",
        "Комплексные",
        "Чекапы",
        "Биохимия",
        "Гормоны",
        "Иммунитет",
        "Витамины",
        "Аллергены",
        "Анализ крови",
        "Анализ мочи",
        "Анализ кала",
        "Только в клинике"
    )

    // Функция для получения новостй с сервера
    // Дата создания: 09.03.2023 09:44
    // Автор: Triangle
    fun loadNews() {
        _news.clear()
        message.value = null
        isSuccessLoadNews.value = null

        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val json = apiService.loadNews()

                if (json.code() == 200) {
                    _news.addAll(json.body()!!)
                    isSuccessLoadNews.value = true
                } else {
                    isSuccessLoadCatalog.value = false
                    message.value = "Ошибка 422"
                }
            } catch (e: Exception) {
                isSuccessLoadCatalog.value = false
                message.value = e.message
            }
        }
    }

    // Функция для получения каталога анализов
    // Дата создания: 09.03.2023 09:44
    // Автор: Triangle
    fun loadCatalog() {
        _analysis.clear()
        message.value = null
        isSuccessLoadCatalog.value = null

        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val json = apiService.loadCatalog()

                if (json.code() == 200) {
                    _analysis.addAll(json.body()!!)
                    isSuccessLoadCatalog.value = true
                } else {
                    isSuccessLoadCatalog.value = false
                    message.value = "Ошибка 422"
                }
            } catch (e: Exception) {
                isSuccessLoadCatalog.value = false
                message.value = e.message
            }
        }
    }
}