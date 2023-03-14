package com.triangle.n12medic2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// Модель пользователя
// Дата создания: 09.03.2023 09:46
// Автор: Triangle
@Keep
data class User(
    @SerializedName("firstname") var firstName: String,
    @SerializedName("lastname") var lastname: String,
    @SerializedName("middlename") var patronymic: String,
    @SerializedName("bith") var birthday: String,
    @SerializedName("pol") var gender: String,
    @SerializedName("image") var image: String,
<<<<<<< HEAD
    @SerializedName("id") var id: Int? = null,
=======
>>>>>>> Session-51
    @SerializedName("cart") var cart: MutableList<CartItem> = ArrayList<CartItem>().toMutableList(),
)