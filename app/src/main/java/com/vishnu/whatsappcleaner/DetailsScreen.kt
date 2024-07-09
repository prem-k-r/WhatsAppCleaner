package com.vishnu.whatsappcleaner

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(navController: NavHostController, viewModel: MainViewModel) {

    val listDirectory = navController.previousBackStackEntry?.savedStateHandle?.get<ListDirectory>(
        Constants.DETAILS_LIST_ITEM
    )

    if (listDirectory == null) { // FIXME!
        Toast.makeText(
            navController.context,
            "Some went wrong!",
            Toast.LENGTH_SHORT
        ).show()

        navController.popBackStack(Constants.SCREEN_HOME, false)
        return
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

            Banner(Modifier.padding(16.dp),
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 24.sp)) {
                        append(listDirectory?.size)
                    }
                    withStyle(SpanStyle(fontSize = 12.sp)) {
                        append(" MB")
                    }
                })
        }
    }
}