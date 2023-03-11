package com.triangle.n12medic2.ui.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.R
import com.triangle.n12medic2.common.OrderService
import com.triangle.n12medic2.model.Address
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.ui.theme.iconsColor
import com.triangle.n12medic2.ui.theme.inputStroke
import com.triangle.n12medic2.view.ManageCardActivity
import java.text.SimpleDateFormat
import java.util.Calendar

// Экран выбора времени
// Дата создания: 11.03.2023 18:17
// Автор: Хасанов Альберт
@SuppressLint("SimpleDateFormat")
@Composable
fun TimeSelectBottomSheet(
    onTimeChange: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    var displayDateFormat = SimpleDateFormat("d")
    var displayDate by rememberSaveable {
        mutableStateOf("")
    }

    val calendar = Calendar.getInstance()

    val mYear: Int = calendar.get(Calendar.YEAR)
    val mMonth: Int = calendar.get(Calendar.MONTH)
    val mDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = DatePickerDialog(
        mContext,
        {_, year: Int, month: Int, day: Int ->
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            val date = sdf.parse("$year.$month.$year")

            var monthText = ""
            when(month) {
                0 -> monthText = "Января"
                1 -> monthText = "Февраля"
                2 -> monthText = "Марта"
                3 -> monthText = "Апреля"
                4 -> monthText = "Мая"
                5-> monthText = "Июня"
                6 -> monthText ="Июля"
                7 -> monthText ="Августа"
                8 -> monthText ="Сентября"
                9 -> monthText ="Октября"
                10 -> monthText ="Ноября"
                11 -> monthText ="Декабря"
            }

            displayDate = "${displayDateFormat.format(date)} $month"
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                    text = "Дата и время",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                AppIconButton(
                    icon = painterResource(id = R.drawable.ic_close),
                    onClick = onDismiss,
                    shape = CircleShape,
                    size = 26.dp
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                value = displayDate,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .padding(end = 12.5.dp),
                singleLine = true,
                readOnly = true,
                label = {
                    Text(
                        text = "Выберите дату",
                        fontSize = 14.sp,
                        color = descriptionColor
                    )
                },
                trailingIcon = {
                     IconButton(onClick = {
                         datePicker.show()
                     }) {
                         Icon(
                             painter = painterResource(id = R.drawable.ic_expand),
                             contentDescription = "",
                             tint = descriptionColor
                         )
                     }
                },
                changeBorder = false
            )
        }
    }
}