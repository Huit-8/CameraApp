package com.example.cameraapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.cameraapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //写真をとるために必要な変数を定義
    private var imageCapture: ImageCapture? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startCamera()
    }

    //カメラの画像を画面に出すメソッドを作る
    private fun startCamera(){
        //カメラを使うためのデータを取得
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //ライフサイクルにカメラを使うための値を宣言
            val cameraProvider:ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            //写真を撮るための値を宣言
            imageCapture = ImageCapture.Builder().build()

            // 外カメで定義
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            //トライキャッチ処理を書く
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,cameraSelector,preview,imageCapture
                )
            }catch (exc: Exception){
                Log.e("CameraX-sample","カメラの起動に失敗しました",exc)
            }

        },ContextCompat.getMainExecutor(this))
    }
}