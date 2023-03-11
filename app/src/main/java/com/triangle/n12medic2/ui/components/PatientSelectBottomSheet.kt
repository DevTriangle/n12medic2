package com.triangle.n12medic2.ui.components

import android.content.Context
import android.content.Intent
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

// Нижний экран выбора пациента
// Дата создания: 11.03.2023 15:14
// Автор: Хасанов Альберт
@Composable
fun PatientSelectBottomSheet(
    patientOnChange: (User) -> Unit,
    patientList: MutableList<User>,
    onDismiss: () -> Unit,
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

    var selectedPatient by remember { mutableStateOf(patientList[0]) }

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
                    text = "Выбор пациента",
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
            for (patient in patientList.distinct()) {
                    PatientCard(
                        user = patient,
                        selected = patient == selectedPatient,
                        onSelect = {
                            selectedPatient = patient
                        }
                    )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            AppOutlinedButton(
                label = "Добавить пациента",
                onClick = {
                    val addIntent = Intent(mContext, ManageCardActivity::class.java)
                    addIntent.putExtra("addNew", true)

                    mContext.startActivity(addIntent)
                },
                fontSize = 15.sp,
                border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            AppButton(
                modifier = Modifier.fillMaxWidth(),
                label = "Подтвердить",
                onClick = {
                    patientOnChange(selectedPatient)
                }
            )
        }
    }
}