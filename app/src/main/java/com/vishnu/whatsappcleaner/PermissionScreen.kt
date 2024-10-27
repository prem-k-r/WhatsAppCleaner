package com.vishnu.whatsappcleaner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PermissionScreen(
    navController: NavHostController,
    permissionsGranted: Pair<Boolean, Boolean>,
    requestPermission: () -> Unit,
    chooseDirectory: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(Modifier.padding(horizontal = 8.dp)) {

            Text(
                modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 0.dp),
                text = "Welcome!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Thanks for installing WhatsApp Cleaner. Please follow this quick setup to get started.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                text = "1. Please grant the required permissions to WhatsApp Cleaner",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
            )

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = !permissionsGranted.first,
                content = {
                    Text(
                        if (!permissionsGranted.first)
                            "Grant storage permissions"
                        else
                            "Storage permission granted"
                    )
                },
                onClick = {
                    requestPermission()
                }
            )

            if (!permissionsGranted.first)
                Spacer(Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                text = "2. Pleae grant access to the WhatsApp directory as shown in the picture below",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
            )

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = permissionsGranted.first && !permissionsGranted.second,
                content = {
                    Text(
                        if (!permissionsGranted.first)
                            "Grant storage permissions"
                        else
                            "Choose WhatsApp directory"
                    )
                },
                onClick = {
                    chooseDirectory()
                },
            )

            if (permissionsGranted.first && !permissionsGranted.second)
                GlideImage(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    model = R.drawable.permission_hint,
                    contentScale = ContentScale.Inside,
                    loading = placeholder(R.drawable.image),
                    failure = placeholder(R.drawable.error),
                    contentDescription = "permission hint"
                )
        }
    }
}