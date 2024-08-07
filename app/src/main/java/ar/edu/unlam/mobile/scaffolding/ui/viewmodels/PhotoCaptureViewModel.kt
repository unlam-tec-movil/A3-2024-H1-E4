package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoCaptureViewModel
    @Inject
    constructor() : ViewModel() {
        private val _bitmap = MutableStateFlow<Bitmap?>(null)
        val bitmap: StateFlow<Bitmap?> = _bitmap.asStateFlow()

        private val _permissionGranted = MutableStateFlow(false)
        val permissionGranted: StateFlow<Boolean> = _permissionGranted

        fun setBitmap(newBitmap: Bitmap?) {
            _bitmap.value = newBitmap
        }

        fun setPermissionGranted(granted: Boolean) {
            _permissionGranted.value = granted
        }
    }
