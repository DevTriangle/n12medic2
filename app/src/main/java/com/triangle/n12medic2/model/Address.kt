package com.triangle.n12medic2.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("alt") val alt: String,
    @SerializedName("flat") val flat: String,
    @SerializedName("entrance") val entrance: String,
    @SerializedName("floor") val floor: String,
    @SerializedName("doorPhone") val doorPhone: String,
)
