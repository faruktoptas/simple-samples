package com.example.qrsample

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(
    private val barcodeListener: (qrCodes: List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC
        )
        .build()

    private val scanner = BarcodeScanning.getClient(options)


    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {

        val visionImage = InputImage.fromMediaImage(image.image!!, image.imageInfo.rotationDegrees)

        scanner.process(visionImage)
            .addOnSuccessListener { barcodes ->
                barcodeListener(barcodes)
            }
            .addOnFailureListener {

            }
            .addOnCompleteListener {
                image.close()
            }

    }


}