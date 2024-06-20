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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Title(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .padding(8.dp),
        text = text,
        fontSize = 36.sp,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun CustomButton(modifier: Modifier, text: String, onclick: () -> Unit) {
    TextButton(
        modifier = modifier
            .padding(8.dp, 8.dp, 8.dp, 0.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(64.dp),
        contentPadding = PaddingValues(16.dp),
        onClick = onclick
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append(text)
                }
            }, fontSize = 24.sp, fontWeight = FontWeight.SemiBold
        )
    }
}