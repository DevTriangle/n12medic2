package com.triangle.n12medic2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.triangle.n12medic2.ui.theme.N12medic2Theme

class AuthActivity : ComponentActivity() {
    // Активити авторизации и регистрации
    // Дата создания: 06.03.2023 15:10
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("authActivity"),
                    color = MaterialTheme.colors.background,
                ) {

                }
            }
        }
    }
}