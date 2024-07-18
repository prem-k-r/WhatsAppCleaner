package com.vishnu.whatsappcleaner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(navController: NavHostController, viewModel: MainViewModel) {

    val listDirectory =
        navController.previousBackStackEntry?.savedStateHandle?.get<ListDirectory>(
            Constants.DETAILS_LIST_ITEM
        )

    if (listDirectory == null)
        return Surface {}

    var fileList = remember { mutableStateListOf<String>() }
    var sentList = remember { mutableStateListOf<String>() }

    LaunchedEffect(key1 = null) {
        viewModel.getFileList(listDirectory.path).observeForever {
            fileList.clear()
            fileList.addAll(it)
        }

        viewModel.getFileList("${listDirectory.path}%2FSent").observeForever {
            sentList.clear()
            sentList.addAll(it)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier.padding(top = 64.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Title(
                Modifier
                    .padding(0.dp)
                    .align(Alignment.Start), stringResource(R.string.app_name)
            )

            Banner(Modifier.padding(16.dp), listDirectory.size)

            Text("Recieved", style = MaterialTheme.typography.titleLarge)

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(fileList) {
                    ItemCard(it, navController)
                }
            }

            Text("Sent", style = MaterialTheme.typography.titleLarge)

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(sentList) {
                    ItemCard(it, navController)
                }
            }
        }
    }
}
