package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.viewmodels.QrViewModel

@Preview
@Composable
fun AchievementHeader(
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: QrViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.background(color = MaterialTheme.colorScheme.primary),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier =
            modifier
                .fillMaxWidth()
                .padding(start = 30.dp),
        ) {
            Text(
                text = "Logros",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(start = 95.dp),
            )
            QrScannerButton(viewModel)
        }
    }
}
