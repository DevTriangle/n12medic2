package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.ui.theme.inputBG
import com.triangle.n12medic2.ui.theme.inputStroke
import com.triangle.n12medic2.ui.theme.primaryInactiveColor

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
    isLoading: Boolean = false
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
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(17.dp),
                color = Color.White,
                strokeWidth = 1.dp
            )
        } else {
            Text(
                text = label,
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSize
            )
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
                .padding(4.dp),
            tint = descriptionColor
        )
    }
}