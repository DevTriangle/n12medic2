package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.components.AppTextButton
import com.triangle.n12medic2.ui.components.OnboardComponent
import com.triangle.n12medic2.ui.theme.N12medic2Theme

class OnboardActivity : ComponentActivity() {
    // Активити приветсвенных экранов (Onboard)
    // Дата создания: 06.03.2023 14:44
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnboardScreen()
                }
            }
        }
    }

    // Содержание актививти OnboardActivity
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun OnboardScreen() {
        val mContext = LocalContext.current
        val pagerState = rememberPagerState()

        val screenAnalyzes = mapOf(
            "title" to "Анализы",
            "description" to "Экспресс сбор и получение проб",
            "image" to painterResource(id = R.drawable.onboard_1)
        )

        val screenNotifications = mapOf(
            "title" to "Уведомления",
            "description" to "Вы быстро узнаете о результатах",
            "image" to painterResource(id = R.drawable.onboard_2)
        )

        val screenMonitoring = mapOf(
            "title" to "Мониторинг",
            "description" to "Наши врачи всегда наблюдают за вашими показателями здоровья",
            "image" to painterResource(id = R.drawable.onboard_3)
        )

        val screenList = listOf(
            screenAnalyzes, screenNotifications, screenMonitoring
        )

        var buttonSkipLabel by rememberSaveable { mutableStateOf("Пропустить") }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect() {
                buttonSkipLabel = if (pagerState.currentPage == pagerState.pageCount - 1) {
                    "Завершить"
                } else {
                    "Пропустить"
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 65.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AppTextButton(
                    modifier = Modifier
                        .padding(start = 30.dp),
                    label = buttonSkipLabel,
                    textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.SemiBold),
                    onClick = {
                        val authIntent = Intent(mContext, AuthActivity::class.java)
                        startActivity(authIntent)

                        saveCompleteTag()
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.logo_shape),
                    contentDescription = ""
                )
            }
            HorizontalPager(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .fillMaxHeight()
                    .testTag("pager"),
                count = screenList.size,
                state = pagerState
            ) { index ->
                OnboardComponent(
                    title = screenList[index]["title"].toString(),
                    description = screenList[index]["description"].toString(),
                    image = screenList[index]["image"] as Painter,
                    currentIndex = pagerState.currentPage
                )
            }
        }
    }

    private fun saveCompleteTag() {
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)

        with(sharedPreferences.edit()) {
            putBoolean("isOnboardCompleted", true)
            apply()
        }
    }
}