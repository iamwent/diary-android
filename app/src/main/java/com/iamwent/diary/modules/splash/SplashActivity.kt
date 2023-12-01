package com.iamwent.diary.modules.splash

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.iamwent.diary.R
import com.iamwent.diary.modules.year.YearActivity

class SplashActivity : ComponentActivity() {
    private val runnable = Runnable {
        YearActivity.start(this@SplashActivity)
        finish()
    }
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestSdcardPermission()
        YearActivity.start(this@SplashActivity)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_PERMISSION_SDCARD -> {
                var hasPermission = true
                var i = 0
                while (i < permissions.size) {
                    if (Manifest.permission.WRITE_EXTERNAL_STORAGE == permissions[i] && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        hasPermission = false
                        break
                    }
                    i++
                }
                if (!hasPermission) {
                    showDialog()
                } else {
                    handler.postDelayed(runnable, 0)
                }
            }
        }
    }

    /**
     * 请求SD卡读写权限
     */
    private fun requestSdcardPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                showDialog()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQ_PERMISSION_SDCARD
                )
            }
        } else {
            handler.postDelayed(runnable, 0)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle(R.string.app_name)
            .setMessage(R.string.request_sdcard_permission)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@SplashActivity,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQ_PERMISSION_SDCARD
                )
            }
            .setCancelable(false)
            .create()
            .show()
    }

    companion object {
        private const val REQ_PERMISSION_SDCARD = 213
    }
}
