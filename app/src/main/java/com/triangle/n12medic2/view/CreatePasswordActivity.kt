package com.triangle.n12medic2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.triangle.n12medic2.ui.theme.N12medic2Theme

class CreatePasswordActivity : ComponentActivity() {
    // Экран создания пароля
    // Дата создания: 08.03.2023 13:49
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }

    // Содержание экрана создания пароля
    // Дата создания: 08.03.2023 13:49
    // Автор: Triangle
    @Composable
    fun CreatePasswordScreen() {

    }
}