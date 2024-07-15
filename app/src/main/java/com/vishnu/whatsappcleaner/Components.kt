package com.vishnu.whatsappcleaner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer


@Composable
fun Title(modifier: Modifier, text: String) {
    Text(
        modifier = modifier
            .padding(8.dp),
        text = text,
        fontSize = 36.sp,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Banner(modifier: Modifier, text: String) {

    val bgColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onPrimary

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val mod =
            if (text.equals("0 B"))
                Modifier.shimmer()
            else
                Modifier

        Box(
            mod
                .padding(12.dp)
                .fillMaxWidth(0.4f)
                .aspectRatio(1f)
                .shadow(elevation = 16.dp, shape = CircleShape)
                .background(bgColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildAnnotatedString {

                    val split = text.split(" ")

                    withStyle(SpanStyle(fontSize = 32.sp)) {
                        append(split.get(0))
                    }
                    withStyle(SpanStyle(fontSize = 18.sp)) {
                        append(" ${split.get(1)}")
                    }
                },
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = bgColor),
            shape = RoundedCornerShape(64.dp),
            contentPadding = PaddingValues(12.dp),
            onClick = { }
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = textColor)) {
                        append("Cleanup")
                    }
                },
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}