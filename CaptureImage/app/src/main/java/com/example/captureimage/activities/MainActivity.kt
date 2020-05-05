package com.example.captureimage.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Camera.CameraInfo
import android.hardware.Camera.open
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.captureimage.R
import com.example.captureimage.utils.Utils
import com.example.captureimage.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        binding.IvCapturePhoto.setOnClickListener {
              askCameraPermission()
        }
    }


    private fun askCameraPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(this,
                 arrayOf(android.Manifest.permission.CAMERA),
                 Utils.CAMERA_PERMISSION_CODE
             )

        }
        else {
           openCamera()
        }
    }
    private fun openCamera() {

        val camera: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camera.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
        camera.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        camera.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(camera,
            Utils.CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode== Utils.CAMERA_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                openCamera()
            }
            else{
                Toast.makeText(this,"Permission is required to use Camera",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== Utils.CAMERA_REQUEST_CODE) {
            var image: Bitmap = data?.extras?.get("data") as Bitmap
            IvCapturePhoto.setImageBitmap(image)
        }
    }


}
