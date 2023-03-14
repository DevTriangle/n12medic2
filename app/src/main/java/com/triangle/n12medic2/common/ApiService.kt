package com.triangle.n12medic2.common

import com.google.gson.JsonObject
import com.triangle.n12medic2.model.Analysis
import com.triangle.n12medic2.model.News
import com.triangle.n12medic2.model.Order
import com.triangle.n12medic2.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*

// Интерфейс для обращения к серверу через API
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
interface ApiService {

    @Headers(
        "accept: application/json"
    )
    @POST("sendCode")
    suspend fun sendCode(@Header("email") email: String): Response<JsonObject>

    @Headers(
        "accept: application/json"
    )
    @POST("signin")
    suspend fun signIn(@HeaderMap headers: Map<String, String>): Response<JsonObject>

    @Headers(
        "accept: application/json",
        "Content-Type: application/json"
    )
    @POST("createProfile")
    suspend fun createProfile(@Header("Authorization") token: String, @Body user: User): Response<JsonObject>

    @Headers(
        "accept: application/json",
        "Content-Type: application/json"
    )
    @PUT("updateProfile")
    suspend fun updateProfile(@Header("Authorization") token: String, @Body userData: Map<String, String>): Response<JsonObject>

    @Headers(
        "accept: application/json",
    )
    @GET("news")
    suspend fun loadNews(): Response<MutableList<News>>

    @Headers(
        "accept: application/json",
    )
    @GET("catalog")
    suspend fun loadCatalog(): Response<MutableList<Analysis>>

    @Headers(
        "accept: application/json",
<<<<<<< HEAD
        "Content-Type: application/json"
    )
    @GET("order")
=======
    )
    @POST("order")
>>>>>>> Session-51
    suspend fun sendOrder(@Header("Authorization") token: String, @Body order: Order): Response<JsonObject>

    companion object {
        var apiService: ApiService? = null

        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://medic.madskill.ru/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

            return apiService!!
        }
    }
}