package com.uniandes.vinylhub.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uniandes.vinylhub.VinylHubApplication
import com.uniandes.vinylhub.presentation.navigation.NavRoutes
import com.uniandes.vinylhub.presentation.ui.screens.AlbumListScreen
import com.uniandes.vinylhub.presentation.ui.screens.HomeScreen
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel
import com.uniandes.vinylhub.ui.theme.VinylHubTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var albumViewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inyectar dependencias
        (application as VinylHubApplication).appComponent.inject(this)

        setContent {
            VinylHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VinylHubApp(albumViewModel)
                }
            }
        }
    }
}

@Composable
fun VinylHubApp(albumViewModel: AlbumViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(
                onNavigateToAlbums = {
                    navController.navigate(NavRoutes.AlbumList.route)
                }
            )
        }
        composable(NavRoutes.AlbumList.route) {
            AlbumListScreen(
                viewModel = albumViewModel,
                onAlbumClick = { albumId ->
                    navController.navigate(NavRoutes.AlbumDetail.createRoute(albumId))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VinylHubAppPreview() {
    VinylHubTheme {
        // Preview con viewModel null
        AlbumListScreen(
            viewModel = null,
            onAlbumClick = {}
        )
    }
}

