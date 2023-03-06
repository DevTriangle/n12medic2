package com.triangle.n12medic2.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {
    // Активити начального экрана
    // Дата создания: 06.03.2023 14:38
    // Автор: Triangle
    
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mContext = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)

            val isOnboardCompleted = sharedPreferences.getBoolean("isOnboardCompleted", false)

            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SplashScreenContent()
                }
            }

            coroutineScope.launch {
                delay(2000)

                if (isOnboardCompleted) {
                    val authIntent = Intent(mContext, AuthActivity::class.java)
                    startActivity(authIntent)
                } else {
                    val onBoardIntent = Intent(mContext, OnboardActivity::class.java)
                    startActivity(onBoardIntent)
                }
            }
        }
    }
    
    // Содержание экрана SplashScreen
    @Composable
    fun SplashScreenContent() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.splash_bg),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.splash_logo),
                contentDescription = ""
            )
        }
    }
}