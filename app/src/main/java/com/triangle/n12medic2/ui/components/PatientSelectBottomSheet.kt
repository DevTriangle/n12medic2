package com.triangle.n12medic2.ui.components

<<<<<<< HEAD
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
=======
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat.startActivity
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar

// Экран выбора пациента
// Дата создания: 14.03.2023 10:33
// Автор: Triangle
@Composable
fun PatientSelectBottomSheet(
    userList: MutableList<User>,
    onPatientChange: (User) -> Unit,
    onCloseClick: () -> Unit
>>>>>>> Session-51
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", Context.MODE_PRIVATE)

<<<<<<< HEAD
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
=======
    var selectedUser by remember { mutableStateOf(User("", "", "" ,"" , "" , "")) }
    
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
                text = "Выбор пациента",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            AppIconButton(
                icon = painterResource(id = R.drawable.ic_close),
                shape = CircleShape,
                size = 24.dp
            ) {
                onCloseClick()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(userList) { usr ->
                UserCard(user = usr, selected = usr == selectedUser, onSelect = { selectedUser = it })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        AppOutlinedButton(
            label = "Добавить пациента",
            onClick = {
                val addIntent = Intent(mContext, ManageCardActivity::class.java)
                addIntent.putExtra("addNew", true)

                mContext.startActivity(addIntent)
            },
            modifier = Modifier
                .fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary
            ),
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal
            ),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Подтвердить",
            enabled = selectedUser != User("", "", "" ,"" , "" , ""),
            onClick = {
                onPatientChange(selectedUser)
            }
        )
>>>>>>> Session-51
    }
}