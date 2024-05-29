package ar.edu.unlam.mobile.scaffolding.domain.services.location

import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate

interface LocationService {
    fun startLocation()

    fun stopLocation()

    fun getCurrentLocation(): Coordinate
}
