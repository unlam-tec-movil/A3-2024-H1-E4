package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// TODO: Borrar

@HiltViewModel
class AchievementViewModel
    @Inject
    constructor(application: Application) :
    AndroidViewModel(application) {
        private val sensorManager =
            application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        private val sensorEventListener =
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    // Para evitar recomposiciones sin mover el dispositivo
                    if (event?.values?.get(0)!! < -1 || event?.values?.get(0)!! > 1) {
                        _sensorValue.value = event?.values?.get(0)!!
                    }
                }

                override fun onAccuracyChanged(
                    sensor: Sensor?,
                    accuracy: Int,
                ) {
                }
            }

        private val _sensorValue = MutableStateFlow(0F)
        val sensorValue = _sensorValue.asStateFlow()

        init {
            sensor.also {
                sensorManager.registerListener(
                    sensorEventListener,
                    it,
                    SensorManager.SENSOR_DELAY_FASTEST,
                    SensorManager.SENSOR_DELAY_FASTEST,
                )
            }
        }
    }
