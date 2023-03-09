package com.triangle.n12medic2.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.triangle.n12medic2.ui.components.BottomNavigationBar
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.R
import com.triangle.n12medic2.ui.theme.iconsColor
import com.triangle.n12medic2.view.screens.AnalyzesScreen
import com.triangle.n12medic2.viewmodel.AnalyzesViewModel

class HomeActivity : ComponentActivity() {
    // Активити главного экрана с навигацией через нижнее меню
    // Дата создания: 09.03.2023 08:48
    // Автор: Triangle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar {
                                BottomNavigationItem(
                                    selected = backStackEntry.value?.destination?.route == "analyzes",
                                    onClick = {
                                        navController.navigate("analyzes")
                                              },
                                    label = {
                                        Text(text = "Анализы")
                                    },
                                    selectedContentColor = MaterialTheme.colors.primary,
                                    unselectedContentColor = iconsColor,
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_analyzes),
                                            contentDescription = ""
                                        )
                                    }
                                )
                                BottomNavigationItem(
                                    selected = backStackEntry.value?.destination?.route == "results",
                                    onClick = { navController.navigate("results") },
                                    label = {
                                        Text(text = "Результаты")
                                    },
                                    selectedContentColor = MaterialTheme.colors.primary,
                                    unselectedContentColor = iconsColor,
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_results),
                                            contentDescription = ""
                                        )
                                    }
                                )
                                BottomNavigationItem(
                                    selected = backStackEntry.value?.destination?.route == "support",
                                    onClick = { navController.navigate("support") },
                                    label = {
                                        Text(text = "Поддержка")
                                    },
                                    selectedContentColor = MaterialTheme.colors.primary,
                                    unselectedContentColor = iconsColor,
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_support),
                                            contentDescription = ""
                                        )
                                    }
                                )
                                BottomNavigationItem(
                                    selected = backStackEntry.value?.destination?.route == "profile",
                                    onClick = { navController.navigate("profile") },
                                    label = {
                                        Text(text = "Профиль")
                                    },
                                    selectedContentColor = MaterialTheme.colors.primary,
                                    unselectedContentColor = iconsColor,
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_profile),
                                            contentDescription = ""
                                        )
                                    }
                                )
                            }
                        }
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            Navigation(navHostController = navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Navigation(navHostController: NavHostController) {
        val analyzesViewModel = ViewModelProvider(this)[AnalyzesViewModel::class.java]

        NavHost(
            navController = navHostController,
            startDestination = "analyzes"
        ) {
            composable("analyzes") {
                AnalyzesScreen(analyzesViewModel)
            }
            composable("results") {

            }
            composable("support") {

            }
            composable("profile") {

            }
        }
    }
}