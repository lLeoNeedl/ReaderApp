package com.example.readerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.readerapp.ui.pages.details.ImageDetailsRoute
import com.example.readerapp.ui.pages.details.ImageDetailsScreen
import com.example.readerapp.ui.pages.list.PagesRoute
import com.example.readerapp.ui.pages.list.PagesScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = PagesRoute
    ) {

        composable<PagesRoute> {
            PagesScreen(
                viewModel = koinViewModel(),
                onImageClick = { content ->
                    navController.navigateLaunchSingleTop(
                        route = ImageDetailsRoute(
                            id = content.id
                        )
                    )
                }
            )
        }

        composable<ImageDetailsRoute> {
            ImageDetailsScreen(
                viewModel = koinViewModel(),
                onBackClick = navController::navigateUp
            )
        }
    }
}