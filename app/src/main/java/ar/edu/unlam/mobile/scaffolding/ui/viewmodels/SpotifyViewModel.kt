package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.spotify.CurrentTrack
import ar.edu.unlam.mobile.scaffolding.domain.services.spotify.SpotifyManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpotifyViewModel(application: Application) : AndroidViewModel(application)  {

    val spotifyManager = SpotifyManager(application.applicationContext)

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    private val _currentTrack = MutableStateFlow<CurrentTrack?>(null)
    var currentTrack: StateFlow<CurrentTrack?> = _currentTrack

    fun connectToSpotify(clientId: String, redirectUri: String) {
        viewModelScope.launch {
            spotifyManager.connect(clientId, redirectUri).collect { connected ->
                _isConnected.value = connected
                if (connected) {
                    spotifyManager.getCurrentlyPlayingTrack().collect { trackInfo ->
                        _currentTrack.value = trackInfo
                        _currentTrack.value!!.imageUri = spotifyManager.imageUrl(trackInfo.imageUri)
                        currentTrack = _currentTrack
                        println(trackInfo.trackName)
                        println(trackInfo.artistName)
                        println(spotifyManager.imageUrl(trackInfo.imageUri))
                    }
                }
            }
        }
    }

    fun disconnectFromSpotify() {
        spotifyManager.disconnect()
        _isConnected.value = false
        _currentTrack.value = null
    }

}
