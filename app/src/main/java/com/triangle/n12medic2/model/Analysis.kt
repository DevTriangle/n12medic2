package com.triangle.n12medic2.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// Модель анализа
// Дата создания: 09.03.2023 09:46
// Автор: Triangle
@Keep
data class Analysis(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: String,
    @SerializedName("category") val category: String,
    @SerializedName("time_result") val time_result: String,
    @SerializedName("preparation") val preparation: String,
    @SerializedName("bio") val bio: String,
)