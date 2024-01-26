package com.example.coen390_poject.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.coen390_poject.R;

public class SplashActivity extends AbsRuntimePermission {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}
