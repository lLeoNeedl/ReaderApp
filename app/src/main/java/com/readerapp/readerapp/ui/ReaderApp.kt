package com.readerapp.readerapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.readerapp.readerapp.ui.navigation.AppNavHost
import com.readerapp.readerapp.ui.theme.ReaderAppTheme

@Composable
fun ReaderApp() {

    val navController = rememberNavController()

    ReaderAppTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AppNavHost(
                navController = navController
            )
        }
    }
}