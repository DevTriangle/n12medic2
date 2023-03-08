package com.triangle.n12medic2.common

import android.content.SharedPreferences

class PasswordManager {
    fun savePassword(sharedPreferences: SharedPreferences, password: String) {
        with(sharedPreferences.edit()) {
            putString("password", password)
            putBoolean("isPasswordSkipped", false)
            apply()
        }
    }
}