package com.triangle.n12medic2.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.triangle.n12medic2.model.Address
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.model.Order
import com.triangle.n12medic2.model.User

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
<<<<<<< HEAD
>>>>>>> Session-51
=======

    // Метод для сохранения заказа
    // Дата создания: 14.03.2023 13:49
    // Автор: Triangle
    fun saveOrder(sharedPreferences: SharedPreferences, order: Order, selectedUsers: MutableList<User>) {
        val orderJson = Gson().toJson(order)
        val users = Gson().toJson(selectedUsers)

        with(sharedPreferences.edit()) {
            putString("order", orderJson)
            putString("users", users)
            apply()
        }
    }

    // Метод для получения заказа
    // Дата создания: 14.03.2023 13:49
    // Автор: Triangle
    fun loadOrder(sharedPreferences: SharedPreferences): Order? {
        return Gson().fromJson(sharedPreferences.getString("order", null), Order::class.java)
    }

    // Метод для получения заказа
    // Дата создания: 14.03.2023 13:49
    // Автор: Triangle
    fun loadOrderPatients(sharedPreferences: SharedPreferences): MutableList<User> {
        val users: MutableList<User> = ArrayList<User>().toMutableList()
        val usersJson = Gson().fromJson(sharedPreferences.getString("cart", "[]"), JsonArray::class.java)

        usersJson.forEach {
            val tUser = it.asJsonObject

            users.add(
                User(
                    tUser.get("firstname").asString,
                    tUser.get("lastname").asString,
                    tUser.get("middlename").asString,
                    tUser.get("bith").asString,
                    tUser.get("pol").asString,
                    tUser.get("image").asString,
                    tUser.get("cart") as MutableList<CartItem>
                )
            )
        }

        return users
    }
>>>>>>> Session-51
}