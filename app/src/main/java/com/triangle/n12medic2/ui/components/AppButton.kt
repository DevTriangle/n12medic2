package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle

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