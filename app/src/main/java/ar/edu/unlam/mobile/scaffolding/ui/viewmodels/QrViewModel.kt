package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.services.qr.QrManager
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class QrViewModel (val qrManager: QrManager): ViewModel() {

    private var _scanResult = MutableStateFlow("")
    var scanResult = _scanResult.asStateFlow()

    private var _qrBitmap = MutableStateFlow<Bitmap?>(null)
    var qrBitmap = _qrBitmap.asStateFlow()

    private var _isScanMatch = MutableStateFlow(false)
    var isScanMatch = _isScanMatch.asStateFlow()

    fun generateQRCode(newText: String){
        _qrBitmap.value = if (newText.isNotEmpty()) {
            qrManager.generateQRCode(newText)
        } else {
            null
        }
    }

    fun scanQRCode(scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>) {
        qrManager.scanQRCode(scanLauncher)
    }

    fun updateScanResult(result: String) {
        _scanResult.value = result
    }

    fun compareJson(contents: String) {
        _isScanMatch.value = qrManager.compareJsonKeys(contents, qrManager.expectedJson)
    }

}