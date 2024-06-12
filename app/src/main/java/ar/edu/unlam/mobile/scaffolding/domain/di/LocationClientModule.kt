package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.domain.services.location.DefaultLocationClient
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationClientModule {
    @Binds
    abstract fun bindsLocationClient(defaultLocationClient: DefaultLocationClient): LocationClient
}
