package com.triangle.n12medic2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import com.triangle.n12medic2.model.Order
import kotlinx.coroutines.launch

// ViewModel для отправки и обработки заказов
// Дата создания: 14.03.2023 12:34
// Автор: Triangle
class OrderViewModel: ViewModel() {
    val isSuccess = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()

    // Метод для отправки заказа на сервер
    // Дата создания: 14.03.2023 12:35
    // Автор: Triangle
    fun sendOrder(
        order: Order,
        token: String
    ) {
        isSuccess.value = null
        message.value = null

        val t = token.replace("\"", "")
        val apiService = ApiService.getInstance()

        viewModelScope.launch {
            try {
                val json = apiService.sendOrder("Bearer $t", order)

                if (json.code() == 200) {
                    isSuccess.value = true
                } else if (json.code() == 401) {
                    message.value = "Вы не авторизованы"
                } else {
                    message.value = json.message()
                }
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }
}