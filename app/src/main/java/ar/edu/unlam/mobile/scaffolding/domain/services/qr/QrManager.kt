package ar.edu.unlam.mobile.scaffolding.domain.services.qr

import android.graphics.Bitmap
import androidx.activity.compose.ManagedActivityResultLauncher
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class QrManager{

    val expectedJsonString = """
        {
            "distotal": "255",
            "tiempototal": 33,
            "calorias": 4500,
            "velmax": 140,
            "velprom": 355
        }
    """
    
    val expectedJson: JsonObject = JsonParser.parseString(expectedJsonString).asJsonObject


    fun generateQRCode(text: String): Bitmap? {

            val qrCodeWriter = QRCodeWriter()
            val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val barcodeEncoder = BarcodeEncoder()
            return barcodeEncoder.createBitmap(bitMatrix)

    }

    fun scanQRCode(
        scanLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>,
    ) {
        val scanInitiate = ScanOptions()
        scanInitiate.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        scanInitiate.setOrientationLocked(false)
        scanInitiate.setPrompt("Escanea un codigo QR")
        scanInitiate.setBeepEnabled(true)
        scanInitiate.setBarcodeImageEnabled(true)
        scanInitiate.setCameraId(0)
        scanLauncher.launch(scanInitiate)
    }

    fun compareJsonKeys(scannedJsonString: String, expectedJson: JsonObject): Boolean {
        return try {
            val scannedJson = JsonParser.parseString(scannedJsonString).asJsonObject
            scannedJson.keySet() == expectedJson.keySet()
        } catch (e: JsonSyntaxException) {
            false
        }
    }

}