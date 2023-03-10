package com.triangle.n12medic2.view.screens

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
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
import com.triangle.n12medic2.common.UserService
import com.triangle.n12medic2.model.User
import com.triangle.n12medic2.ui.components.AppButton
import com.triangle.n12medic2.ui.components.AppTextButton
import com.triangle.n12medic2.ui.components.AppTextField
import com.triangle.n12medic2.ui.components.LoadingDialog
import com.triangle.n12medic2.ui.theme.captionColor
import com.triangle.n12medic2.view.ManageCardActivity
import com.triangle.n12medic2.viewmodel.ManageCardViewModel

// Экран "Профиль"
// Дата создания: 09.03.2023 10:08
// Автор: Triangle
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    viewModel: ManageCardViewModel,
    navController: NavHostController,
    photoResultLauncher: ActivityResultLauncher<Intent>,
    videoResultLauncher: ActivityResultLauncher<Intent>
) {
    val mContext = LocalContext.current
    val sharedPreferences = mContext.getSharedPreferences("shared", ComponentActivity.MODE_PRIVATE)

    val token = sharedPreferences.getString("token", "")

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var selectPhotoDialog by rememberSaveable { mutableStateOf(false) }
    var isErrorVisible by rememberSaveable { mutableStateOf(false) }

    val isSuccess by viewModel.isSuccess.observeAsState()
    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            navController.navigate("analyzes")
        }
    }

    val errorMessage by viewModel.errorMessage.observeAsState()
    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            isErrorVisible = true
        }
    }

    val userList: MutableList<User> = remember { mutableStateListOf() }
    LaunchedEffect(Unit) {
        userList.addAll(UserService().loadPatients(sharedPreferences))

        if (userList.isEmpty()) {
            val intent = Intent(mContext, ManageCardActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    var firstName by rememberSaveable { mutableStateOf("") }
    var patronymic by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var birthday by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }

    var isChanged by rememberSaveable { mutableStateOf(false) }
    val image by viewModel.imageBitmap.observeAsState()
    LaunchedEffect(image) {
        if (image != null) {
            isChanged = true
        }
    }

    if (userList.isNotEmpty()) {
        firstName = userList[0].firstName
        patronymic = userList[0].patronymic
        lastName = userList[0].lastname
        birthday = userList[0].birthday
        gender = userList[0].gender
    }

    val exoPlayer = ExoPlayer.Builder(mContext).build().apply {
        val dataSourceFactory = DefaultDataSourceFactory(mContext, mContext.packageName)
        val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(viewModel.vid.value!!))

        prepare(source)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Карта пациента",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(7.dp))
            if (userList.isNotEmpty()) {
                if (viewModel.isVideo.value == true) {
                    AndroidView(
                        modifier = Modifier
                            .width(150.dp)
                            .height(125.dp),
                        factory = {
                        PlayerView(it).apply {
                            player = exoPlayer
                            useController = false

                            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
                            exoPlayer.play()
                        }
                    })
                } else {
                    GlideImage(
                        modifier = Modifier
                            .width(150.dp)
                            .height(125.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD9D9D9).copy(0.5f))
                            .clickable {
                                selectPhotoDialog = true
                            },
                        imageModel = if (image != null) image else userList[0].image
                    )
                }
            } else {
                Image(
                    modifier = Modifier
                        .width(150.dp)
                        .height(125.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD9D9D9).copy(0.5f))
                        .clickable {
                            selectPhotoDialog = true
                        },
                    painter = painterResource(id = R.drawable.ic_photo),
                    contentDescription = "")
            }
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Без карты пациента вы не сможете заказать анализы.",
                fontSize = 14.sp,
                color = captionColor,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "В картах пациентов будут храниться результаты анализов вас и ваших близких.",
                fontSize = 14.sp,
                color = captionColor,
            )
            Spacer(modifier = Modifier.height(32.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Имя",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = firstName,
                onValueChange = {
                    firstName = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Отчество",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = patronymic,
                onValueChange = {
                    patronymic = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Фамилия",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = lastName,
                onValueChange = {
                    lastName = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            AppTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Дата рождения",
                        color = captionColor,
                        fontSize = 15.sp
                    )
                },
                value = birthday,
                onValueChange = {
                    birthday = it
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth(),
                expanded = isExpanded,
                onExpandedChange = {
                    isExpanded = it
                }
            ) {
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Пол",
                            color = captionColor,
                            fontSize = 15.sp
                        )
                    },
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = { isExpanded = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_expand),
                                contentDescription = ""
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = { gender = "Мужской" }
                    ) {
                        Text(text = "Мужской")
                    }
                    DropdownMenuItem(
                        onClick = { gender = "Женский" }
                    ) {
                        Text(text = "Женский")
                    }
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
            AppButton(
                modifier = Modifier
                    .fillMaxWidth(),
                label = "Сохранить",
                enabled = firstName.isNotBlank() && patronymic.isNotBlank() && lastName.isNotBlank() && birthday.isNotBlank() && gender.isNotBlank(),
                onClick = {
                    Log.d(ContentValues.TAG, "ManageCardScreen: $token")

                    userList[0] = User(firstName, patronymic, lastName, birthday, gender, userList[0].image)
                    UserService().savePatients(sharedPreferences, userList)

                    viewModel.updateProfile(firstName, patronymic, lastName, birthday, gender, token!!)
                    isLoading = true
                }
            )
        }
    }

    if (selectPhotoDialog) {
        AlertDialog(
            onDismissRequest = { selectPhotoDialog = false },
            title = {
                Text(text = "Изменение изображения профиля")
            },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AppButton(
                        label = "Фото",
                        onClick = {
                            val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                            photoResultLauncher.launch(photoIntent)
                        }
                    )
                    AppButton(
                        label = "Видео",
                        onClick = {
                            val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                            videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3)

                            videoResultLauncher.launch(videoIntent)
                        }
                    )
                }
            }
        )
    }

    if (isErrorVisible) {
        AlertDialog(
            title = { Text(
                text = "Ошибка",
                fontSize = 18.sp
            ) },
            text = { Text(
                text = errorMessage.toString(),
                fontSize = 15.sp
            ) },
            onDismissRequest = {isErrorVisible = false},
            confirmButton = {
                AppTextButton(
                    label = "Ок",
                    onClick = { isErrorVisible = false }
                )
            }
        )
    }

    if (isLoading) {
        LoadingDialog()
    }
}