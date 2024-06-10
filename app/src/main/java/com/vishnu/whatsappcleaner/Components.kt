package com.vishnu.whatsappcleaner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(modifier: Modifier, text: String, onclick: () -> Unit) {
    var buttonText by remember { mutableStateOf(text) }

    TextButton(
        modifier = modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
        border = BorderStroke(
            1.dp, MaterialTheme.colorScheme.onSurface
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(
                0xFF292D32
            )
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 12.dp),
        onClick = onclick
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(buttonText)
                }
            }, fontSize = 16.sp, fontWeight = FontWeight.Light
        )
    }
}