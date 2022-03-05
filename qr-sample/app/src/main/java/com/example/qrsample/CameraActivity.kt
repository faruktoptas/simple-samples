package com.example.qrsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class CameraActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10
    }

    private var cameraProvider: ProcessCameraProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        if (isCameraPermissionGranted()) {
            initialize()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    private fun initialize() {
        val provider = ProcessCameraProvider.getInstance(applicationContext)
        provider.addListener({
            cameraProvider = provider.get()
            startCamera()
        }, ContextCompat.getMainExecutor(applicationContext))
    }

    private fun startCamera() {

        val preview = Preview.Builder().build()
        val previewView: PreviewView = findViewById(R.id.preview_view)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(applicationContext),
            QrCodeAnalyzer {
                if (it.isNotEmpty()) {
                    Toast.makeText(this, "Read value:\n${it.first().rawValue}", Toast.LENGTH_SHORT)
                        .show()
                    cameraProvider?.unbindAll()
                    finish()
                }
            })

        cameraProvider?.bindToLifecycle(
            this as LifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            imageAnalysis,
            preview
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission =
            ContextCompat.checkSelfPermission(baseContext, Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                initialize()
            } else {
                Toast.makeText(this, "Camera permission is required.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}