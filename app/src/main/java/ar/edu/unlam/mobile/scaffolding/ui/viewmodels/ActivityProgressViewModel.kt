package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityProgressViewModel
    @Inject
    constructor() : ViewModel() {
        private var startTime: Long = 0L
        private var isRunning: Boolean = false

        @Suppress("ktlint:standard:backing-property-naming")
        private var _eleapsedTimeState = MutableStateFlow(0L)
        private var job: Job? = null

        val eleapsedTimeState = _eleapsedTimeState.asStateFlow()

        fun start() {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - eleapsedTimeState.value
                isRunning = true
                job =
                    viewModelScope.launch {
                        while (isRunning) {
                            delay(1000)
                            _eleapsedTimeState.value = System.currentTimeMillis() - startTime
                        }
                    }
            }
        }

        fun stop() {
            if (isRunning) {
                isRunning = false
                job?.cancel()
            }
        }

        fun reset() {
            isRunning = false
            job?.cancel()
            _eleapsedTimeState.value = 0L
        }
    }
