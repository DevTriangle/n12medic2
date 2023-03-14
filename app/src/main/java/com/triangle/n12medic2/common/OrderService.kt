package com.triangle.n12medic2.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.triangle.n12medic2.model.Address

// Класс для сохранения и получения данных о заказе
<<<<<<< HEAD
// Дата создания: 11.03.2023 13:51
// Автор: Хасанов Альберт
class OrderService {

    // Функция сохранения адреса в память устройства
    // Дата создания: 11.03.2023 14:50
    // Автор: Хасанов Альберт
=======
// Дата создания: 13.03.2023 11:38
// Автор: Triangle
class OrderService {
    // Метод для сохранения адреса
    // Дата создания: 13.03.2023 12:02
    // Автор: Triangle
>>>>>>> Session-51
    fun saveAddress(sharedPreferences: SharedPreferences, address: Address) {
        val addressJson = Gson().toJson(address)

        with(sharedPreferences.edit()) {
            putString("address", addressJson)
            apply()
        }
    }
<<<<<<< HEAD
=======

    // Метод для получения адреса
    // Дата создания: 14.03.2023 8:58
    // Автор: Triangle
    fun loadAddress(sharedPreferences: SharedPreferences): Address? {
        return Gson().fromJson(sharedPreferences.getString("address", null), Address::class.java)
    }
>>>>>>> Session-51
}