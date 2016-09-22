package com.iamwent.diary.modules.splash;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.iamwent.diary.R;
import com.iamwent.diary.data.DiaryRepository;
import com.iamwent.diary.modules.year.YearActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends Activity {

    private static final int REQ_PERMISSION_SDCARD = 213;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            YearActivity.start(SplashActivity.this);
            finish();
        }
    };

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DiaryRepository.getInstance(this);
        requestSdcardPermission();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQ_PERMISSION_SDCARD: //请求Sdcard的读写权限
                boolean hasPermission = true;
                for (int i = 0; i < permissions.length; i++) {
                    if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i]) && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        hasPermission = false;
                        break;
                    }
                }
                if (!hasPermission) {
                    showDialog();
                } else {
                    handler.postDelayed(runnable, 0);
                }
                break;
        }
    }


    /**
     * 请求SD卡读写权限
     */
    private void requestSdcardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showDialog();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION_SDCARD);
            }
        } else {
            handler.postDelayed(runnable, 0);
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(R.string.request_sdcard_permission)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION_SDCARD);
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
