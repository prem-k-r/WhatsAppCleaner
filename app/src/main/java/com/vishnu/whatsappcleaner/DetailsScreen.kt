package com.vishnu.whatsappcleaner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreen(navController: NavHostController, viewModel: MainViewModel) {

    val listDirectory = navController.previousBackStackEntry?.savedStateHandle?.get<ListDirectory>(
        Constants.DETAILS_LIST_ITEM
    )

    if (listDirectory == null) return Surface {}

    var fileList = remember { mutableStateListOf<ListFile>() }
    var sentList = remember { mutableStateListOf<ListFile>() }

    var isInProgress by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isInProgress) {
        viewModel.getFileList(listDirectory.path).observeForever {
            fileList.clear()
            fileList.addAll(it)
        }

        if (listDirectory.hasSent) viewModel.getFileList("${listDirectory.path}%2FSent")
            .observeForever {
                sentList.clear()
                sentList.addAll(it)
            }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier.padding(top = 64.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Title(
                Modifier
                    .padding(0.dp)
                    .align(Alignment.Start), stringResource(R.string.app_name)
            )

            Banner(Modifier.padding(16.dp), listDirectory.size) {
                showDialog = true
            }

            val pagerState = rememberPagerState(pageCount = {
                if (listDirectory.hasSent) 2 else 1
            })

            if (isInProgress) LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
            )

            HorizontalPager(
                modifier = Modifier.weight(1f), state = pagerState
            ) { page ->

                var list = if (page == 0) fileList
                else sentList

                if (list.isNotEmpty()) {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(3),
                    ) {
                        items(list) {
                            ItemCard(it, navController) {
                                it.isSelected = !it.isSelected
                            }
                        }
                    }
                } else Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    painter = painterResource(id = R.drawable.empty),
                    contentDescription = "empty",
                )
            }

            if (listDirectory.hasSent) Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                val coroutineScope = rememberCoroutineScope()
                val arr = arrayOf("Received", "Sent")

                for (s in arr) {

                    TextButton(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(4.dp, 8.dp, 4.dp, 0.dp)
                        .border(
                            BorderStroke(
                                2.dp,
                                if (arr[pagerState.settledPage] != s) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.background,
                            ),
                            RoundedCornerShape(64.dp),
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (arr[pagerState.settledPage] == s) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(64.dp),
                        contentPadding = PaddingValues(4.dp),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(
                                    arr.indexOf(s)
                                )
                            }
                        }) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                                    append(s)
                                }
                            },
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }

                }

            }

        }
    }

    if (showDialog) ConfirmationDialog(
        onDismissRequest = {
            showDialog = false
        },
        onConfirmation = {
            viewModel.delete(fileList.filter { it.isSelected }).observeForever {
                isInProgress = it
            }
            showDialog = false
        },
        fileList.filter { it.isSelected }, // TODO:
        navController
    )
}

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    list: List<ListFile>,
    navController: NavHostController
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.Start),
                    text = "Confirm Cleanup",
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .align(Alignment.Start),
                    text = "The following files will be deleted.",
                    style = MaterialTheme.typography.bodyMedium,
                )

                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(3),
                ) {
                    items(list) {
                        ItemCard(it, navController, selectionEnabled = false) {}
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Spacer(Modifier.weight(1f))

                    TextButton(
                        modifier = Modifier,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                        onClick = onConfirmation,
                        content = {
                            Text(
                                text = "Confirm", style = MaterialTheme.typography.titleMedium,
                            )
                        },
                    )
                }
            }
        }
    }
}