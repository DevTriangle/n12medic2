package com.triangle.n12medic2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.triangle.n12medic2.ui.theme.descriptionColor
import com.triangle.n12medic2.ui.theme.inputBG
import java.util.Locale.Category

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    name: String,
    selected: Boolean,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = { onClick(name) }),
        elevation = 0.dp,
        backgroundColor = if (selected) MaterialTheme.colors.primary else inputBG
    ) {
        Text(
            modifier = Modifier
                .padding(20.dp, 14.dp),
            text = name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) Color.White else descriptionColor
        )
    }
}