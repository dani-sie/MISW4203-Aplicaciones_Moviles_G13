package com.uniandes.vinylhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uniandes.vinylhub.data.repository.AlbumRepository
import com.uniandes.vinylhub.data.repository.ArtistRepository
import com.uniandes.vinylhub.data.repository.CollectorRepository
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel
import com.uniandes.vinylhub.presentation.viewmodel.ArtistViewModel
import com.uniandes.vinylhub.presentation.viewmodel.CollectorViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    
    @Singleton
    @Provides
    fun provideAlbumViewModel(albumRepository: AlbumRepository): AlbumViewModel {
        return AlbumViewModel(albumRepository)
    }
    
    @Singleton
    @Provides
    fun provideArtistViewModel(artistRepository: ArtistRepository): ArtistViewModel {
        return ArtistViewModel(artistRepository)
    }
    
    @Singleton
    @Provides
    fun provideCollectorViewModel(collectorRepository: CollectorRepository): CollectorViewModel {
        return CollectorViewModel(collectorRepository)
    }
}

