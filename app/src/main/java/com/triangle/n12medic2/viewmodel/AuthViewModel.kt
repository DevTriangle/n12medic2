package com.triangle.n12medic2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    var message = MutableLiveData<String>()
    var isSuccess = MutableLiveData<Boolean>()

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