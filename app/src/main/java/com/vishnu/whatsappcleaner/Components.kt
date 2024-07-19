package com.vishnu.whatsappcleaner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.valentinilk.shimmer.shimmer
import java.io.File


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

    var onClick: () -> Unit
    var modifier: Modifier

    if (listDirectory.path.contains("com.vishnu.whatsappcleaner.loading")) {
        modifier = Modifier.shimmer()
        onClick = { }
    } else {
        modifier = Modifier
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.apply {
                set(Constants.DETAILS_LIST_ITEM, listDirectory)
            }
            navController.navigate(Constants.SCREEN_DETAILS)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        onClick = onClick
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCard(
    path: String,
    navController: NavHostController,
    selected: Boolean,
    clickListener: () -> Unit
) {

    val bgColor = MaterialTheme.colorScheme.secondaryContainer

    var clicked by remember { mutableStateOf(selected) }

    var onClick: () -> Unit
    var modifier: Modifier

    if (path.toString().contains("com.vishnu.whatsappcleaner.loading")) {
        modifier = Modifier.shimmer()
        onClick = { }
    } else {
        modifier = Modifier
        onClick = {
            clickListener()
            clicked = !clicked
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        onClick = onClick
    ) {

        val file = File(path)

        Box(Modifier.clip(shape = RoundedCornerShape(8.dp))) {

            // TODO:
//            Checkbox(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .zIndex(1f),
//                checked = clicked,
//                colors = CheckboxDefaults.colors(
//                    checkedColor = MaterialTheme.colorScheme.background,
//                    checkmarkColor = MaterialTheme.colorScheme.onBackground,
//                    uncheckedColor = Color.Transparent,
//                    disabledCheckedColor = Color.Transparent,
//                    disabledUncheckedColor = Color.Transparent,
//                    disabledIndeterminateColor = Color.Transparent,
//                ),
//                onCheckedChange = {
//                    clicked = !clicked
//                }
//            )

            if (file.extension.lowercase() in Constants.EXTENSIONS_IMAGE)
                GlideImage(
                    model = path,
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.drawable.image),
                    failure = placeholder(R.drawable.error),
                    contentDescription = "details list item"
                )
            else if (file.extension.lowercase() in Constants.EXTENSIONS_VIDEO) {
                GlideImage(
                    model = path,
                    contentScale = ContentScale.Crop,
                    loading = placeholder(R.drawable.image),
                    failure = placeholder(R.drawable.error),
                    contentDescription = "details list item"
                )

                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .zIndex(2f),
                    painter = painterResource(id = R.drawable.video),
                    contentDescription = "video",
                )
            } else if (file.extension.lowercase() in Constants.EXTENSIONS_DOCS) {

                Column {

                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.document),
                        contentDescription = "doc",
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        text = file.name,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            } else if (file.extension.lowercase() in Constants.EXTENSIONS_AUDIO) {

                Column {

                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.audio),
                        contentDescription = "doc",
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        text = file.name,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            } else {

                Column {

                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.unknown),
                        contentDescription = "doc",
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        text = file.name,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}