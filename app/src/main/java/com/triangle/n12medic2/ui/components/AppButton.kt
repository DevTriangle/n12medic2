package com.triangle.n12medic2.ui.components

import android.webkit.WebSettings.PluginState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.triangle.n12medic2.R
import com.triangle.n12medic2.model.CartItem
import com.triangle.n12medic2.ui.theme.*

// Текстовая кнопка
@Composable
fun AppTextButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = MaterialTheme.colors.primaryVariant
    ),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    textStyle: TextStyle = LocalTextStyle.current
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        Text(
            text = label,
            style = textStyle
        )
    }

}

@Composable
fun AppButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(
        0.dp, 0.dp, 0.dp, 0.dp, 0.dp
    ),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        disabledBackgroundColor = primaryInactiveColor,
        contentColor = Color.White,
        disabledContentColor = Color.White
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    fontSize: TextUnit = 17.sp,
    isLoading: Boolean = false,
    icon: Painter? = null
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
    ) {
        Row() {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(17.dp),
                    color = Color.White,
                    strokeWidth = 1.dp
                )
            } else {
                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = "",
                        tint = Color.White,

                    )
                }
                Text(
                    text = label,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize
                )
            }
        }
    }
}

@Composable
fun AppOutlinedButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(
        0.dp, 0.dp, 0.dp, 0.dp, 0.dp
    ),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = BorderStroke(1.dp, inputStroke),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color.White,
        disabledBackgroundColor = Color.White
    ),
    contentPadding: PaddingValues = PaddingValues(16.dp),
    fontSize: TextUnit = 17.sp
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = fontSize
        )
    }
}

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    shape: Shape = MaterialTheme.shapes.small,
    size: Dp = 32.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(inputBG)
            .clickable(
                onClick = onClick
            )
    ) {
        Icon(
            painter = icon,
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(3.dp),
            tint = descriptionColor
        )
    }
}

@Composable
fun AppPasswordButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(inputBG)
            .clickable(
                onClick = { onClick(label) }
            )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Кнопка корзины на главном экране
// Дата создания: 10.03.2023 09:24
// Автор: Triangle
@Composable
fun AppCartButton(
    cart: MutableList<CartItem>,
    onClick: () -> Unit
) {
    var sum = 0

    for (item in cart) {
        sum += item.price.toInt()
    }

    Button(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "",
                    tint = Color.White,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "В корзину",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            }
            Text(
                text = "$sum ₽",
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        }
    }
}

@Composable
fun CartButtons(
    modifier: Modifier = Modifier,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    minusEnabled: Boolean,
    plusEnabled: Boolean
) {
    Card(
        modifier = modifier
            .height(38.dp),
        elevation = 0.dp,
        backgroundColor = inputBG
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMinusClick, enabled = minusEnabled) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "",
                    tint = if (minusEnabled) captionColor else iconsColor
                )
            }
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .height(25.dp)
                    .background(inputStroke)
            )
            IconButton(onClick = onPlusClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "",
                    tint = if (plusEnabled) captionColor else iconsColor
                )
            }
        }
    }
}