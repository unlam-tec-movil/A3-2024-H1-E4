package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationClient
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationService
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideLocationService {
    @Singleton
    @Provides
    fun providesLocationService(locationClient: LocationClient): LocationService {
        return LocationServiceImpl(locationClient)
    }
}
