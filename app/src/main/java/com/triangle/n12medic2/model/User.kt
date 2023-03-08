package com.triangle.n12medic2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("middlename") val patronymic: String,
    @SerializedName("bith") val birthday: String,
    @SerializedName("pol") val gender: String,
    @SerializedName("image") val image: String,
)