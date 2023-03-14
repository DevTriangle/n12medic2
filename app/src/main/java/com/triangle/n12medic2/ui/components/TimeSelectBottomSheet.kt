package com.triangle.n12medic2.ui.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.app.Activity
import android.app.DatePickerDialog
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar

// Экран выбора времени
// Дата создания: 14.03.2023 09:24
// Автор: Triangle
@SuppressLint("SimpleDateFormat")
@Composable
fun TimeSelectBottomSheet(
    onTimeChange: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    val displayTimeFormat = SimpleDateFormat("d")
    var displayTime by rememberSaveable { mutableStateOf("") }
    var selectedTime by rememberSaveable { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val mYear = calendar.get(Calendar.YEAR)
    val mMonth = calendar.get(Calendar.MONTH)
    val mDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        mContext,
        { _, year: Int, month: Int, day: Int ->
            val sdf = SimpleDateFormat("dd.MM.yyy")
            val sTime = sdf.parse("$day.$month.$year")

            var displayMonth = ""

            when(month) {
                0 -> displayMonth = "Января"
                1 -> displayMonth = "Февраля"
                2 -> displayMonth = "Марта"
                3 -> displayMonth = "Апреля"
                4 -> displayMonth = "Мая"
                5 -> displayMonth = "Июня"
                6 -> displayMonth = "Июля"
                7 -> displayMonth = "Августа"
                8 -> displayMonth = "Сентября"
                9 -> displayMonth = "Октября"
                10 -> displayMonth = "Ноября"
                11 -> displayMonth = "Декабря"
            }

            if (LocalDateTime.now().dayOfMonth == day) {
                displayTime = "Сегодня, ${displayTimeFormat.format(sTime)} $displayMonth"
            } else {
                displayTime = "${displayTimeFormat.format(sTime)} $displayMonth"
            }
        }, mYear, mMonth, mDay
    )

    val times = listOf(
        "10:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "18:00",
        "19:00",
    )

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
                text = "Дата и время",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AppIconButton(
                icon = painterResource(id = R.drawable.ic_close),
                shape = CircleShape,
                size = 24.dp
            ) {

            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        AppTextField(
            value = displayTime,
            onValueChange = {},
            label = {
                Text(
                    text = "Выберите дату",
                    fontSize = 16.sp,
                    color = descriptionColor,
                    fontWeight = FontWeight.Medium
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { datePickerDialog.show() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_expand), 
                        contentDescription = "",
                        tint = descriptionColor
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Выберите время",
            fontSize = 16.sp,
            color = descriptionColor,
            fontWeight = FontWeight.Medium
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(70.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(times) { t ->
                TimeChip(time = t, selected = selectedTime == t, onSelect = {
                    selectedTime = it
                })
            }
        }
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Подтвердить",
            onClick = {
                onTimeChange("$displayTime $selectedTime")
            }
        )
    }
}

// Элемент выбора времени
// Дата создания: 14.03.2023 09:36
// Автор: Triangle
@Composable
private fun TimeChip(
    time: String,
    selected: Boolean,
    onSelect: (String) -> Unit
) {
    Card(
        elevation = 0.dp,
        backgroundColor = if (selected) MaterialTheme.colors.primary else inputBG,
        modifier = Modifier
            .width(70.dp)
            .height(40.dp)
            .clickable {
                onSelect(time)
            }
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp),
            text = time,
            color = if (selected) Color.White else descriptionColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}