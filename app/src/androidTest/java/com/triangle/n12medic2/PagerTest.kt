package com.triangle.n12medic2

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.triangle.n12medic2.view.OnboardActivity
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PagerTest {

    @get:Rule
    val pagerTestRule = createAndroidComposeRule<OnboardActivity>()

    // Изображение и текста из очереди извлекается правильно (в порядке добавления в очередь).
    @Test
    fun pagerQueueTest() {
        pagerTestRule.activity.setContent {
            pagerTestRule.activity.OnboardScreen()
        }

        pagerTestRule.onNodeWithText("Анализы").assertIsDisplayed()

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Уведомления").assertIsDisplayed()

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Мониторинг").assertIsDisplayed()
    }

    // Корректное извлечение элементов из очереди (количество
    // элементов в очереди уменьшается на единицу).
    @Test
    fun pagerItemQueueTest() {
        pagerTestRule.activity.setContent {
            pagerTestRule.activity.OnboardScreen()
        }

        pagerTestRule.onNodeWithText("Анализы").assertIsDisplayed()

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Уведомления").assertIsDisplayed()

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Мониторинг").assertIsDisplayed()
    }

    // В случае, когда в очереди несколько картинок,
    // устанавливается правильная надпись на кнопке.
    @Test
    fun pagerButtonSkipTextTest() {
        pagerTestRule.activity.setContent {
            pagerTestRule.activity.OnboardScreen()
        }

        pagerTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Завершить").assertIsDisplayed()
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeRight() }
        pagerTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
    }

    // Случай, когда в очереди осталось только одно изображение,
    // надпись на кнопке должна измениться на "Завершить".
    @Test
    fun pagerButtonSkipTest() {
        pagerTestRule.activity.setContent {
            pagerTestRule.activity.OnboardScreen()
        }

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Завершить").assertIsDisplayed()
    }

    // Если очередь пустая и пользователь нажал на кнопку
    // “Завершить”, происходит открытие экрана «Вход и регистрация/не
    // заполнено» приложения. Если очередь не пустая – переход отсутствует.
    @Test
    fun pagerClickButtonSkipTest() {
        pagerTestRule.activity.setContent {
            pagerTestRule.activity.OnboardScreen()
        }

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Завершить").performClick()

        pagerTestRule.onNodeWithTag("authActivity").assertExists()
    }

    // Наличие вызова метода сохранения флага об успешном
    // прохождении приветствия пользователем.
    @Test
    fun saveIsCompletedTest() {
        pagerTestRule.activity.setContent {
            pagerTestRule.activity.OnboardScreen()
        }

        pagerTestRule.onNodeWithText("Пропустить").performClick()
        val isCompleted = pagerTestRule.activity.getSharedPreferences("shared", Context.MODE_PRIVATE).getBoolean("isOnboardCompleted", false)
        assertEquals(isCompleted, true)
    }
}