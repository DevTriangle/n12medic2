package com.triangle.n12medic2.common

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.triangle.n12medic2.model.User

// Класс для сохранения и получения данных о пациентах
// Дата создания: 10.03.2023 12:15
// Автор: Triangle
class UserService {
    fun loadPatients(sharedPreferences: SharedPreferences): MutableList<User> {
        val users: MutableList<User> = ArrayList<User>().toMutableList()
        val usersJson = Gson().fromJson(sharedPreferences.getString("users", "[]"), JsonArray::class.java)

        usersJson.forEach { usr ->
            val tUser = usr.asJsonObject

            users.add(
                User(
                    tUser.get("firstname").asString,
                    tUser.get("lastname").asString,
                    tUser.get("middlename").asString,
                    tUser.get("bith").asString,
                    tUser.get("pol").asString,
                    tUser.get("image").asString,
                )
            )
        }

        return users
    }

    fun savePatients(sharedPreferences: SharedPreferences, patients: MutableList<User>) {
        val usersJson = Gson().toJson(patients)

        with(sharedPreferences.edit()) {
            putString("users", usersJson)
            apply()
        }
    }

    fun addMainPatient(sharedPreferences: SharedPreferences, patients: MutableList<User>, patient: User) {
        val pList = patients

        patients.add(0, patient)

        savePatients(sharedPreferences, pList)
    }
}