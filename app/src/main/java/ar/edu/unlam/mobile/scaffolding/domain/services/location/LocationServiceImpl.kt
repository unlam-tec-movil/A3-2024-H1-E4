package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.collection.MutableFloatList
import androidx.collection.mutableFloatListOf
import androidx.core.app.NotificationCompat
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ServiceScoped
class LocationServiceImpl
    @Inject
    constructor(private val locationClient: LocationClient) : Service(), LocationService {
        private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        override val locationCoordinates: MutableStateFlow<MutableList<Coordinate>> =
            MutableStateFlow(
                mutableListOf(),
            )
        override val locationSpeeds: MutableFloatList = mutableFloatListOf()

//        locationClient =
//            DefaultLocationClient(
//                applicationContext,
//                LocationServices.getFusedLocationProviderClient(applicationContext),
//            )

        override fun onBind(p0: Intent?): IBinder? = null

        override fun getLocationCoordinates(): List<Coordinate> = locationCoordinates.value

        override fun getSpeeds(): List<Float> = speeds

        override fun onStartCommand(
            intent: Intent?,
            flags: Int,
            startId: Int,
        ): Int {
            when (intent?.action) {
                ACTION_START -> startLocation()
                ACTION_STOP -> stopLocation()
            }
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onDestroy() {
            super.onDestroy()
            serviceScope.cancel()
        }

        override fun startLocation() {
            val notification =
                NotificationCompat.Builder(this, "location")
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.baseline_play_arrow_24)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val threeSeconds = 3L
            locationClient.getLocationUpdates(threeSeconds)
                .catch { exception -> exception.printStackTrace() }
                .onEach { location ->

                    val latitude = location.latitude
                    val longitude = location.longitude
                    val currentCoordinate = Coordinate(latitude = latitude, longitude = longitude)
                    val updatedNotificacion =
                        notification.setContentText("Location coordinates: (lat=$latitude, long=$longitude)")
                    locationCoordinates.value.add(currentCoordinate)
                    coordinatesState = locationCoordinates.value
                    speeds.add(location.speed)
                    Log.i("CNO Location", "LAT=${coordinatesState.lastOrNull()?.latitude}")
                    Log.i("CNO Location", "LONG=${coordinatesState.lastOrNull()?.longitude}")
                    Log.i("CNO Location", "Speed: ${location.speed}")
                    notificationManager.notify(1, updatedNotificacion.build())
                }.launchIn(serviceScope)
            startForeground(1, notification.build())
        }

        override fun stopLocation() {
            Log.i("CNO Coordinates SERV", "${locationCoordinates.value}")

            stopForeground(STOP_FOREGROUND_DETACH)
            stopSelf()
        }

        companion object {
            const val ACTION_START = "ACTION_START"
            const val ACTION_STOP = "ACTION_STOP"
            var coordinatesState: MutableList<Coordinate> = mutableListOf()
            var speeds: MutableList<Float> = mutableListOf()
        }
    }
