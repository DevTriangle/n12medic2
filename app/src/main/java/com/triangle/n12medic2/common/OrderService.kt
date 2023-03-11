package com.triangle.n12medic2.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.triangle.n12medic2.model.Address

// Класс для сохранения и получения данных о заказе
// Дата создания: 11.03.2023 13:51
// Автор: Хасанов Альберт
class OrderService {

    // Функция сохранения адреса в память устройства
    // Дата создания: 11.03.2023 14:50
    // Автор: Хасанов Альберт
    fun saveAddress(sharedPreferences: SharedPreferences, address: Address) {
        val addressJson = Gson().toJson(address)

        with(sharedPreferences.edit()) {
            putString("address", addressJson)
            apply()
        }
    }
}