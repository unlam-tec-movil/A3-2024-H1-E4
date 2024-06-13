package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.localization.FusedLocationClient
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationClientModule {
    @Binds
    abstract fun bindsLocationClient(defaultLocationClient: FusedLocationClient): LocationClient
}
