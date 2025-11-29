package com.uniandes.vinylhub.presentation.navigation

sealed class NavRoutes(val route: String) {
    object AlbumList : NavRoutes("album_list")
    object AlbumDetail : NavRoutes("album_detail/{albumId}") {
        fun createRoute(albumId: Int) = "album_detail/$albumId"
    }
    object CreateAlbum : NavRoutes("create_album")
    object AssociateTracks : NavRoutes("associate_tracks")

    object ArtistList : NavRoutes("artist_list")
    object ArtistDetail : NavRoutes("artist_detail/{artistId}") {
        fun createRoute(artistId: Int) = "artist_detail/$artistId"
    }

    object CollectorList : NavRoutes("collector_list")
    object CollectorDetail : NavRoutes("collector_detail/{collectorId}") {
        fun createRoute(collectorId: Int) = "collector_detail/$collectorId"
    }

    object Home : NavRoutes("home")
}

