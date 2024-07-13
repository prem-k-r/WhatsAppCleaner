package com.vishnu.whatsappcleaner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        fontWeight = FontWeight.Medium
    )
}

@Composable
fun Banner(modifier: Modifier, text: String) {
    Card(
        modifier
            .fillMaxWidth()
            .aspectRatio(2f),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {

            val mod =
                if (text.equals("0 B"))
                    Modifier.shimmer()
                else
                    Modifier

            Box(
                mod
                    .padding(8.dp)
                    .size(128.dp)
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = buildAnnotatedString {

                        val split = text.split(" ")

                        withStyle(SpanStyle(fontSize = 24.sp)) {
                            append(split.get(0))
                        }
                        withStyle(SpanStyle(fontSize = 16.sp)) {
                            append(" ${split.get(1)}")
                        }
                    },
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

            TextButton(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(64.dp),
                contentPadding = PaddingValues(12.dp),
                onClick = { }
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSecondary)) {
                            append("Cleanup")
                        }
                    }, fontSize = 24.sp, fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}