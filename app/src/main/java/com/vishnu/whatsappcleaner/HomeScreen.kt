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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.padding(vertical = 64.dp, horizontal = 16.dp)) {

            val uri = viewModel.getHomeUri().observeAsState()

            if (uri.value != null) {
                val list = viewModel.listDirectories(
                    uri.value!!
                ).observeAsState()

                if (list.value != null) {

                    LazyColumn {
                        items(list.value!!) {
                            Text(text = it.toString())
                        }
                    }

                }
            }
        }
    }
}
