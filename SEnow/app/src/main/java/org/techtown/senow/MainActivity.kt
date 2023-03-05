package org.techtown.senow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat

class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var mOpenCvCameraView: CameraBridgeViewBase

    companion object {
        init {
            if (!OpenCVLoader.initDebug()) {
                Log.d("OpenCV", "Error while initializing OpenCV")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mOpenCvCameraView = findViewById(R.id.camera_view)
        mOpenCvCameraView.setCvCameraViewListener(this)
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun onCameraViewStopped() {
        TODO("Not yet implemented")
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        TODO("Not yet implemented")
    }
}