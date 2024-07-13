package com.vishnu.whatsappcleaner

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishnu.whatsappcleaner.ui.theme.WhatsAppCleanerTheme


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK
                    && Build.VERSION.SDK_INT >= VERSION_CODES.Q
                    && result.data != null
                    && result.data!!.data != null
                    && result.data!!.data!!.path != null
                ) {
                    contentResolver.takePersistableUriPermission(
                        result.data!!.data!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )

                    viewModel.saveHomeUri(result.data!!.data!!.path!!.split(":")[1])

                    // terrible hack!
                    val intent = intent;
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        this, "Permission not granted, exiting...", Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(application)
        ).get(MainViewModel::class.java)

        setContent {
            WhatsAppCleanerTheme {

                var startDestination =
                    if (contentResolver.persistedUriPermissions.isNotEmpty()) Constants.SCREEN_HOME
                    else Constants.SCREEN_PERMISSION

                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(route = Constants.SCREEN_PERMISSION) {
                        PermissionScreen(navController) {
                            resultLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                                if (Build.VERSION.SDK_INT >= VERSION_CODES.O) putExtra(
                                    DocumentsContract.EXTRA_INITIAL_URI,
                                    Uri.parse(Constants.WHATSAPP_HOME_URI)
                                )
                            })
                        }
                    }

                    composable(route = Constants.SCREEN_HOME) {
                        HomeScreen(navController, viewModel)
                    }

                    composable(route = Constants.SCREEN_DETAILS) {
                        DetailsScreen(navController, viewModel)
                    }


                }
            }
        }
    }
}