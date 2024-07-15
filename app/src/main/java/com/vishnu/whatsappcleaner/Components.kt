package com.vishnu.whatsappcleaner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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

    val bgColor = MaterialTheme.colorScheme.primaryContainer
    val textColor = MaterialTheme.colorScheme.onPrimaryContainer

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

@Composable
fun SingleCard(
    listDirectory: ListDirectory,
    navController: NavHostController,
) {

    val bgColor = MaterialTheme.colorScheme.secondaryContainer
    val textColor = MaterialTheme.colorScheme.onSecondaryContainer

    val modifier =
        if (listDirectory.path.contains("com.vishnu.whatsappcleaner.loading"))
            Modifier.shimmer()
        else
            Modifier

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.apply {
                set(Constants.DETAILS_LIST_ITEM, listDirectory)
            }
            navController.navigate(Constants.SCREEN_DETAILS)
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                Modifier
                    .padding(12.dp)
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .background(bgColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = ImageVector.vectorResource(id = listDirectory.icon),
                    contentDescription = "icon",
                    tint = textColor
                )

            }

            Column(
                Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(4.dp),
                    text = listDirectory.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(4.dp),
                    text = listDirectory.size,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor,
                )
            }
        }
    }
}
