package com.triangle.n12medic2.ui.components

import androidx.compose.runtime.Composable
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.skydoves.landscapist.glide.GlideImage
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.CartService
import com.triangle.n12medic2.common.OrderService
import com.triangle.n12medic2.common.UserService
import com.triangle.n12medic2.model.Address
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppTextButton
import com.triangle.n12medic2.ui.components.AppTextField
import com.triangle.n12medic2.ui.components.LoadingDialog
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.ui.theme.iconsColor
import com.triangle.n12medic2.ui.theme.inputBG
import com.triangle.n12medic2.view.ManageCardActivity
import com.triangle.n12medic2.viewmodel.ManageCardViewModel

// Экран выбора адреса
// Дата создания: 14.03.2023 09:24
// Автор: Triangle
@Composable
fun AddressBottomSheet(
    onAddressChange: (String) -> Unit
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    val address = OrderService().loadAddress(sharedPreferences)

    var addressValue by rememberSaveable { mutableStateOf("") }
    var addressName by rememberSaveable { mutableStateOf("") }

    var lon by rememberSaveable { mutableStateOf("") }
    var lat by rememberSaveable { mutableStateOf("") }
    var alt by rememberSaveable { mutableStateOf("") }

    var flat by rememberSaveable { mutableStateOf("") }
    var entrace by rememberSaveable { mutableStateOf("") }
    var floor by rememberSaveable { mutableStateOf("") }

    var doorphone by rememberSaveable { mutableStateOf("") }
    
    var isSave by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (address != null) {
            addressValue = address.address
            lon = address.lat
            lat = address.lon
            alt = address.alt
            flat = address.flat
            entrace = address.entrance
            floor = address.floor
            doorphone = address.doorphone
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Адрес сдачи анализов",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map),
                    contentDescription = "",
                    tint = iconsColor,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            value = addressValue,
            onValueChange = { addressValue = it },
            label = {
                Text(
                    text = "Ваш адрес",
                    fontSize = 14.sp,
                    color = descriptionColor
                )
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .padding(end = 12.5.dp),
                value = lon,
                onValueChange = { lon = it },
                label = {
                    Text(
                        text = "Долгота",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(end = 12.5.dp),
                value = lat,
                onValueChange = { lat = it },
                label = {
                    Text(
                        text = "Широта",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = alt,
                onValueChange = { alt = it },
                label = {
                    Text(
                        text = "Высота",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .padding(end = 12.5.dp),
                value = flat,
                onValueChange = { flat = it },
                label = {
                    Text(
                        text = "Кватира",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
            )
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(0.66f)
                    .padding(end = 12.5.dp),
                value = entrace,
                onValueChange = { entrace = it },
                label = {
                    Text(
                        text = "Подъезд",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
            )
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = floor,
                onValueChange = { floor = it },
                label = {
                    Text(
                        text = "Этаж",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = doorphone,
            onValueChange = { doorphone = it },
            label = {
                Text(
                    text = "Домофон",
                    fontSize = 14.sp,
                    color = descriptionColor
                )
            },
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Сохранить этот адрес?",
                softWrap = true
            )
            Switch(
                checked = isSave,
                onCheckedChange = {isSave = it},
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackAlpha = 1f,
                    checkedTrackColor = MaterialTheme.colors.primary,
                    uncheckedTrackColor = inputBG,
                    uncheckedTrackAlpha = 1f
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(visible = isSave) {
            Column(Modifier.fillMaxWidth()) {
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = addressName,
                    onValueChange = { addressName = it },
                    placeholder = {
                        Text(
                            text = "Название: например дом, работа",
                            fontSize = 15.sp,
                            color = captionColor
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Подтвердить",
            onClick = {
                val fAddress = "$addressValue кв. $flat"
                val add = Address(addressName, addressValue, lon, lat, alt, flat, entrace, floor, doorphone)

                if (isSave) {
                   OrderService().saveAddress(sharedPreferences, add)
                }

                onAddressChange(fAddress)
            }
        )
    }
}