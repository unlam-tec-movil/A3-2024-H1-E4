package ar.edu.unlam.mobile.scaffolding.domain

import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import java.util.Date

object MockEntities {
    val user = User(1, "Juan", "Pérez", 22, 171, 75.0, 150.0, 5000, 300, 10)
    val route = Route(1, 1, 20.0, 6000, 300, 6.5, 5.5, "", Date(1719888024000))
}
