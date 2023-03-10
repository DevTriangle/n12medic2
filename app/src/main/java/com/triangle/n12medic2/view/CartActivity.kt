package com.triangle.n12medic2.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppIconButton
import com.triangle.n12medic2.ui.components.CartComponent
import com.triangle.n12medic2.ui.theme.N12medic2Theme
import com.triangle.n12medic2.ui.theme.iconsColor

// Активити корзины
// Дата создания: 10.03.2023 09:32
// Автор: Triangle
class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            N12medic2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CartScreen()
                }
            }
        }
    }

    // Экран корзины
    // Дата создания: 10.03.2023 09:46
    // Автор: Triangle
    @Composable
    fun CartScreen() {
        val sharedPreferences = this.getSharedPreferences("shared", MODE_PRIVATE)
        val mContext = LocalContext.current

        var cart: MutableList<CartItem> = remember { mutableStateListOf() }
        LaunchedEffect(Unit) {
            cart.addAll(CartService().loadCart(sharedPreferences))
        }

        Scaffold(
            topBar = {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)) {
                    AppIconButton(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        icon = painterResource(id = R.drawable.ic_back),
                        size = 40.dp
                    ) {
                        val intent = Intent(mContext, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Корзина",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = {
                                cart.clear()

                                CartService().saveCart(sharedPreferences, cart)
                            },
                            modifier = Modifier
                                .size(24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_remove),
                                contentDescription = "",
                                tint = iconsColor,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                }
            }
        ) {
            Column(modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        for (item in cart) {
                            CartComponent(
                                cartItem = item,
                                onRemove = {
                                    cart.remove(item)

                                    CartService().saveCart(sharedPreferences, cart)
                                },
                                onMinus = {
                                    val index = cart.indexOfFirst { it.id == item.id }
                                    cart = CartService().minusElement(sharedPreferences, cart, index)

                                    cart.clear()
                                    cart.addAll(CartService().loadCart(sharedPreferences))
                                },
                                onPlus = {
                                    val index = cart.indexOfFirst { it.id == item.id }
                                    cart = CartService().plusElement(sharedPreferences, cart, index)

                                    cart.clear()
                                    cart.addAll(CartService().loadCart(sharedPreferences))
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        var sum = 0

                        for (item in cart) {
                            sum += item.price.toInt() * item.count
                        }

                        Text(
                            text = "Сумма",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "$sum ₽",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                AppButton(
                    label = "Перейти к оформлению заказа",
                    onClick = {
                        val orderIntent = Intent(mContext, OrderActivity::class.java)
                        startActivity(orderIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                )
            }
        }
    }
}