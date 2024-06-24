package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.QrViewModel
import com.journeyapps.barcodescanner.ScanContract
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun QrScannerButton(viewModel: QrViewModel){

    val scanResult by viewModel.scanResult.collectAsState()
    val isScanMatch by viewModel.isScanMatch.collectAsState()

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            viewModel.updateScanResult(result.contents)
            viewModel.compareJson(result.contents)
        }
    )

    IconButton(
        onClick = { viewModel.scanQRCode(scanLauncher) },
        modifier = Modifier.fillMaxSize()
            .padding(start = 30.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
            contentDescription = "QR Scanner",
            tint = Color.Black,
        )
    }

}