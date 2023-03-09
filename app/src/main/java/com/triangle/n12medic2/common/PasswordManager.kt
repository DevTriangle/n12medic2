package com.triangle.n12medic2.common

import android.content.SharedPreferences

// Сохранение пароля
// Дата создания: 09.03.2023 09:44
// Автор: Triangle
class PasswordManager {
    fun savePassword(sharedPreferences: SharedPreferences, password: String) {
        with(sharedPreferences.edit()) {
            putString("password", password)
            putBoolean("isPasswordSkipped", false)
            apply()
        }
    }
}