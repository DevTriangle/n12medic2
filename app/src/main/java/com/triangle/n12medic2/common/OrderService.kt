package com.triangle.n12medic2.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.triangle.n12medic2.model.Address

// Класс для сохранения и получения данных о заказе
// Дата создания: 13.03.2023 11:38
// Автор: Triangle
class OrderService {
    // Метод для сохранения адреса
    // Дата создания: 13.03.2023 12:02
    // Автор: Triangle
    fun saveAddress(sharedPreferences: SharedPreferences, address: Address) {
        val addressJson = Gson().toJson(address)

        with(sharedPreferences.edit()) {
            putString("address", addressJson)
            apply()
        }
    }
}