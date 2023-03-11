package com.triangle.n12medic2.model

import com.google.gson.annotations.SerializedName

// Модель заказа
// Дата создания: 11.03.2023 18:00
// Автор: Хасанов Альберт
data class Order(
    @SerializedName("address") val address: String,
    @SerializedName("date_time") val date_time: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("comment") val comment: String,
    @SerializedName("audio_comment") val audio_comment: String,
    @SerializedName("patients") val patients: List<OrderPatient>,
)

// Модель пациента в заказе
// Дата создания: 11.03.2023 18:00
// Автор: Хасанов Альберт
data class OrderPatient(
    @SerializedName("name") val name: String,
    @SerializedName("items") val items: List<OrderItem>,
)

// Модель услуги в заказе
// Дата создания: 11.03.2023 18:00
// Автор: Хасанов Альберт
data class OrderItem(
    @SerializedName("catalog_id") val catalog_id: Int,
    @SerializedName("price") val price: String,
)


