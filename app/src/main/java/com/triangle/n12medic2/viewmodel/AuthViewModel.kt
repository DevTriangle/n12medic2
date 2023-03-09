package com.triangle.n12medic2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import kotlinx.coroutines.launch

// ViewModel для отправки кода на почту
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
class AuthViewModel: ViewModel() {
    var message = MutableLiveData<String>()
    var isSuccess = MutableLiveData<Boolean>()

    // Отправка кода на почту
    // Дата создания: 09.03.2023 09:44
    // Автор: Triangle
    fun sendCode(email: String) {
        message.value = null
        isSuccess.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val json = apiService.sendCode(email)

                if (json.code() == 200) {
                    isSuccess.value = true
                } else {
                    message.value = json.body()?.get("errors").toString()
                }
            } catch (e: Exception) {
                message.value = e.message
            }
        }
    }
}