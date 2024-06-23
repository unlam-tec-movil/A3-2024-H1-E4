package ar.edu.unlam.mobile.scaffolding.domain.models.spotify

data class CurrentTrack(
    val trackName: String,
    val artistName: String,
    var imageUri: String?,
    val isPaused: Boolean,
)
