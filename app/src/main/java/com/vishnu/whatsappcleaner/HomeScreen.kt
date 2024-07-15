package com.vishnu.whatsappcleaner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {

    var totalSize = remember { mutableStateOf("0 B") }

    var directoryList = remember { mutableStateListOf<ListDirectory>() }

    LaunchedEffect(key1 = null) {
        viewModel.getDirectoryList().observeForever {
            totalSize.value = it.first

            directoryList.clear()
            directoryList.addAll(it.second)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Title(
                modifier = Modifier
                    .padding(0.dp)
                    .align(Alignment.Start),
                text = stringResource(R.string.app_name),
            )

            Banner(Modifier.padding(16.dp), totalSize.value)

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp, 16.dp, 16.dp, 8.dp),
                text = "Select to Explore",
                style = MaterialTheme.typography.titleLarge,
            )

            LazyColumn(Modifier.weight(1f)) {
                items(directoryList) {
                    SingleCard(it, navController)
                }
            }
        }
    }
}

@Composable
fun SingleCard(
    listDirectory: ListDirectory,
    navController: NavHostController,
) {

    val bgColor = MaterialTheme.colorScheme.primaryContainer
    val textColor = MaterialTheme.colorScheme.onPrimaryContainer

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

            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.20f)
                    .aspectRatio(1f),
                imageVector = ImageVector.vectorResource(id = listDirectory.icon),
                contentDescription = "icon"
            )

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
