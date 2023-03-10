package com.triangle.n12medic2.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName

// Модель анализа в корзине
// Дата создания: 10.03.2023 09:15
// Автор: Triangle
data class CartItem(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("price") var price: String,
    @SerializedName("count") var count: Int
)
