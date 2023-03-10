package com.triangle.n12medic2.viewmodel

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import com.triangle.n12medic2.model.User
import kotlinx.coroutines.launch

// ViewModel для отпрвки данных карты на сервер
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
class ManageCardViewModel:ViewModel() {
    var errorMessage = MutableLiveData<String>()
    var isSuccess = MutableLiveData<Boolean>()

    var imageBitmap = MutableLiveData<Bitmap>()
    var vid = MutableLiveData<Uri>(Uri.parse(""))
    var isVideo = MutableLiveData<Boolean>()

    // Изменение отображения фото в профиле
    // Дата создания: 10.03.2023 13:20
    // Автор: Triangle
    fun setImage(image: Bitmap) {
        isVideo.value = false
        imageBitmap.value = image
    }

    // Изменение отображения видео в профиле
    // Дата создания: 10.03.2023 13:20
    // Автор: Triangle
    fun setVideo(video: Uri) {
        isVideo.value = true
        vid.value = video
    }

    // Создание профиля пользователя
    // Дата создания: 09.03.2023 09:44
    // Автор: Triangle
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

    // Изменение профиля пользователя
    // Дата создания: 09.03.2023 09:44
    // Автор: Triangle
    fun updateProfile(
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
                val userData = mapOf(
                    "firstname" to firstName,
                    "lastname" to lastName,
                    "middlename" to patronymic,
                    "bith" to birthday,
                    "pol" to gender,
                )
                val mToken = token.replace("\"", "")
                val json = apiService.updateProfile("Bearer $mToken", userData)

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