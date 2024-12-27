package com.vishnu.whatsappcleaner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {

    var totalSize = remember { mutableStateOf("0 B") }

    var forceReload by remember { mutableStateOf(false) }
    var directoryList = remember { mutableStateListOf<ListDirectory>() }

    forceReload = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(
        Constants.FORCE_RELOAD_FILE_LIST
    ) ?: false

    LaunchedEffect(key1 = forceReload) {

        if (forceReload) {
            totalSize.value = "0 B"
            directoryList.clear()
            directoryList.addAll(ListDirectory.getDirectoryList(Constants._LOADING))
        }

        viewModel.getDirectoryList(forceReload).observeForever {
            totalSize.value = it.first

            directoryList.clear()
            directoryList.addAll(it.second)

            forceReload = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Title(
                Modifier
                    .padding(0.dp)
                    .align(Alignment.Start),
                stringResource(R.string.app_name)
            )

            Banner(Modifier.padding(16.dp), totalSize.value)

            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(16.dp, 16.dp, 16.dp, 8.dp),
                text = "Explore",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
            )

            LazyColumn(Modifier.weight(1f)) {
                items(directoryList) {
                    SingleCard(it, navController)
                }
            }

            if (BuildConfig.DEBUG)
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp),
                    text = "Debug Build ${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.labelSmall,
                )

            BannerAd()
        }
    }
}