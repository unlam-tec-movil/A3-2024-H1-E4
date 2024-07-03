package ar.edu.unlam.mobile.scaffolding.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.PhotoCaptureViewModel

@Composable
fun PhotoCapture(viewModel: PhotoCaptureViewModel) {
    val context = LocalContext.current
    val bitmap by viewModel.bitmap.collectAsState()
    val permissionGranted by viewModel.permissionGranted.collectAsState()

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
                isGranted ->
            viewModel.setPermissionGranted(isGranted)
            if (!isGranted) {
                Log.e("PhotoCapture", "Permission denied")
            }
        }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            viewModel.setPermissionGranted(true)
        }
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { result ->
            viewModel.setBitmap(result)
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (permissionGranted) {
            Button(onClick = { launcher.launch() }) {
                Text(text = "Photo")
            }
        } else {
            Text(text = "Camera permission is required to take photos")
        }

        Spacer(modifier = Modifier.height(16.dp))

        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp),
            )
        }
    }
}
