package com.triangle.n12medic2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.triangle.n12medic2.common.ApiService
import com.triangle.n12medic2.model.*
import kotlinx.coroutines.launch

// ViewModel оформления заказа
// Дата создания: 11.03.2023 18:16
// Автор: Хасанов Альберт
class OrderViewModel: ViewModel() {
    var isSuccess = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    // Отпарвка заказа на сервер
    // Дата создания: 11.03.2023 18:16
    // Автор: Хасанов Альберт
    fun sendOrder(
        address: String,
        date: String,
        userList: MutableList<User>,
        phone: String,
        comment: String,
        token: String
    ) {
        isSuccess.value = null
        message.value = null

        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()

                val mToken = token.replace("\"", "")

                val patientList = ArrayList<OrderPatient>()

                for (u in userList) {
                    val orderItems = ArrayList<OrderItem>()

                    for (o in u.cart) {
                        orderItems.add(OrderItem(catalog_id = o.id, price = o.price))
                    }

                    patientList.add(
                        OrderPatient(u.lastname + " " + u.firstName, orderItems)
                    )
                }

                val json = apiService.sendOrder("Bearer $mToken", Order(address, date, phone, comment, "", patientList))

                if (json.code() == 200) {
                    isSuccess.value = true
                } else if (json.code() == 401) {
                    isSuccess.value = false
                    message.value = "Вы не авторизованы"
                } else {
                    isSuccess.value = false
                    message.value = json.message()
                }
            } catch (e: Exception) {
                message.value = e.message
                isSuccess.value = false
            }
        }
    }
}