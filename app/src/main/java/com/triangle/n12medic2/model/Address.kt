package com.triangle.n12medic2.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
<<<<<<< HEAD
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
=======
    @SerializedName("lon") val lon: String,
    @SerializedName("lat") val lat: String,
>>>>>>> Session-51
    @SerializedName("alt") val alt: String,
    @SerializedName("flat") val flat: String,
    @SerializedName("entrance") val entrance: String,
    @SerializedName("floor") val floor: String,
<<<<<<< HEAD
    @SerializedName("doorPhone") val doorPhone: String,
=======
    @SerializedName("doorphone") val doorphone: String,
>>>>>>> Session-51
)
