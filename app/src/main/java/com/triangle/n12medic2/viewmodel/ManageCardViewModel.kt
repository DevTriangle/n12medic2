package com.triangle.n12medic2.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import com.triangle.n12medic2.model.User
import kotlinx.coroutines.launch

class ManageCardViewModel:ViewModel() {
    var errorMessage = MutableLiveData<String>()
    var isSuccess = MutableLiveData<Boolean>()

    fun createProfile(
        firstName: String,
        patronymic: String,
        lastName: String,
        birthday: String,
        gender: String,
        token: String
    ) {
        errorMessage.value = null
        isSuccess.value = null

        viewModelScope.launch {
            val apiService = ApiService.getInstance()

            try {
                val mToken = token.replace("\"", "")
                val json = apiService.createProfile("Bearer $mToken", User(firstName, patronymic, lastName, birthday, gender, ""))

                if (json.code() == 200) {
                    isSuccess.value = true
                } else {
                    Log.d(TAG, "${json.code()} - ${json.body()?.get(" errors ").toString()}")
                    errorMessage.value = "${json.code()} - ${json.body()?.get(" errors ").toString()}"
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                errorMessage.value = e.message
            }
        }
    }
}