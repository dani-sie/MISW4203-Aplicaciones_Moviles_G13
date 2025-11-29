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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.uniandes.vinylhub.presentation.navigation.NavRoutes
import com.uniandes.vinylhub.presentation.ui.screens.AlbumDetailScreen
import com.uniandes.vinylhub.presentation.ui.screens.AlbumListScreen
import com.uniandes.vinylhub.presentation.ui.screens.ArtistListScreen
import com.uniandes.vinylhub.presentation.ui.screens.CollectorListScreen
import com.uniandes.vinylhub.presentation.ui.screens.CreateAlbumScreen
import com.uniandes.vinylhub.presentation.ui.screens.HomeScreen
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel
import com.uniandes.vinylhub.presentation.viewmodel.ArtistViewModel
import com.uniandes.vinylhub.presentation.viewmodel.CollectorViewModel
import com.uniandes.vinylhub.ui.theme.VinylHubTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var albumViewModel: AlbumViewModel

    @Inject
    lateinit var artistViewModel: ArtistViewModel

    @Inject
    lateinit var collectorViewModel: CollectorViewModel

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
                    VinylHubApp(albumViewModel, artistViewModel, collectorViewModel)
                }
            }
        }
    }
}

@Composable
fun VinylHubApp(albumViewModel: AlbumViewModel, artistViewModel: ArtistViewModel, collectorViewModel: CollectorViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(
                onNavigateToAlbums = {
                    navController.navigate(NavRoutes.AlbumList.route)
                },
                onNavigateToArtists = {
                    navController.navigate(NavRoutes.ArtistList.route)
                },
                onNavigateToCollectors = {
                    navController.navigate(NavRoutes.CollectorList.route)
                },
                onNavigateToCreateAlbum = {
                    navController.navigate(NavRoutes.CreateAlbum.route)
                }
            )
        }
        composable(NavRoutes.AlbumList.route) {
            AlbumListScreen(
                viewModel = albumViewModel,
                onAlbumClick = { albumId ->
                    navController.navigate(NavRoutes.AlbumDetail.createRoute(albumId))
                },
                onBackToHome = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(
            NavRoutes.AlbumDetail.route,
            arguments = listOf(
                navArgument("albumId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getInt("albumId") ?: return@composable
            AlbumDetailScreen(
                albumId = albumId,
                viewModel = albumViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(NavRoutes.ArtistList.route) {
            ArtistListScreen(
                viewModel = artistViewModel,
                onArtistClick = { artistId ->
                    navController.navigate(NavRoutes.ArtistDetail.createRoute(artistId))
                },
                onBackToHome = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(
            NavRoutes.ArtistDetail.route,
            arguments = listOf(
                navArgument("artistId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getInt("artistId") ?: return@composable
            com.uniandes.vinylhub.presentation.ui.screens.ArtistDetailScreen(
                artistId = artistId,
                viewModel = artistViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onAlbumClick = { albumId ->
                    navController.navigate("album_detail/$albumId")
                }
            )
        }
        composable(NavRoutes.CollectorList.route) {
            CollectorListScreen(
                viewModel = collectorViewModel,
                onCollectorClick = { collectorId ->
                    navController.navigate(NavRoutes.CollectorDetail.createRoute(collectorId))
                },
                onBackToHome = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(
            NavRoutes.CollectorDetail.route,
            arguments = listOf(
                navArgument("collectorId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val collectorId = backStackEntry.arguments?.getInt("collectorId") ?: return@composable
            com.uniandes.vinylhub.presentation.ui.screens.CollectorDetailScreen(
                collectorId = collectorId,
                viewModel = collectorViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onArtistClick = { artistId ->
                    navController.navigate("artist_detail/$artistId")
                },
                onAlbumClick = { albumId ->
                    navController.navigate("album_detail/$albumId")
                }
            )
        }
        composable(NavRoutes.CreateAlbum.route) {
            CreateAlbumScreen(
                viewModel = albumViewModel,
                onBackClick = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Home.route) { inclusive = false }
                    }
                },
                onAlbumCreated = { albumId ->
                    // Navegar al detalle del álbum recién creado
                    navController.navigate(NavRoutes.AlbumDetail.createRoute(albumId)) {
                        popUpTo(NavRoutes.Home.route) { inclusive = false }
                    }
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

