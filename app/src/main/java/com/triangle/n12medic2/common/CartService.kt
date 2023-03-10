package com.triangle.n12medic2.common

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.model.CartItem

// Класс для сохранения и получения корзины из памяти устройства
// Дата создания: 10.03.2023 09:15
// Автор: Triangle
class CartService {

    // Функция загрузки корзины
    // Дата создания: 10.03.2023 09:16
    // Автор: Triangle
    fun loadCart(sharedPreferences: SharedPreferences):MutableList<CartItem> {
        val cart: MutableList<CartItem> = ArrayList<CartItem>().toMutableList()
        val cartJson = Gson().fromJson(sharedPreferences.getString("cart", "[]"), JsonArray::class.java)

        cartJson.forEach {
            val cartItem = it.asJsonObject

            cart.add(
                CartItem(
                    cartItem.get("id").asInt,
                    cartItem.get("name").asString,
                    cartItem.get("price").asString,
                    cartItem.get("count").asInt
                )
            )
        }

        return cart
    }

    // Функция сохранения корзины
    // Дата создания: 10.03.2023 09:16
    // Автор: Triangle
    fun saveCart(sharedPreferences: SharedPreferences, cart: MutableList<CartItem>) {
        val cartJson = Gson().toJson(cart)

        with(sharedPreferences.edit()) {
            putString("cart", cartJson)
            apply()
        }
    }

    // Функция уменьшения количества товаров
    // Дата создания: 10.03.2023 10:58
    // Автор: Triangle
    fun minusElement(sharedPreferences: SharedPreferences, cart: MutableList<CartItem>, itemIndex: Int):MutableList<CartItem>  {
        cart[itemIndex].count -= 1

        saveCart(sharedPreferences, cart)

        return cart
    }

    // Функция увеличения количества товаров
    // Дата создания: 10.03.2023 10:58
    // Автор: Triangle
    fun plusElement(sharedPreferences: SharedPreferences, cart: MutableList<CartItem>, itemIndex: Int):MutableList<CartItem>  {
        cart[itemIndex].count += 1

        saveCart(sharedPreferences, cart)

        return cart
    }
}