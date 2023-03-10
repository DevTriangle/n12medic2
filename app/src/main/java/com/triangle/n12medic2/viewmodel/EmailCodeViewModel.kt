package com.triangle.n12medic2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import kotlinx.coroutines.launch

// ViewModel для авторизации пользователя
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
class EmailCodeViewModel:ViewModel() {
    var errorMessage = MutableLiveData<String>()
    var token = MutableLiveData<String>()

    // Авторизация пользователя
    // Дата создания: 09.03.2023 09:44
    // Автор: Triangle
    fun signIn(
        email: String,
        code: String
    ) {
        errorMessage.value = null
        token.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val headers = mapOf(
                    "email" to email,
                    "code" to code
                )
                val json = apiService.signIn(headers)

                if (json.code() == 200) {

                    token.value = json.body()?.get("token").toString()
                } else {
                    errorMessage.value = json.body()?.get("errors").toString()
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}