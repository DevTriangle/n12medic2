package com.triangle.n12medic2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// Модель новости
// Дата создания: 09.03.2023 09:46
// Автор: Triangle
@Keep
data class News(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
)
