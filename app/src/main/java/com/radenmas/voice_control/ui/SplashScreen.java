package com.radenmas.voice_control.ui;

import android.content.Intent;
import android.os.Handler;

import com.radenmas.voice_control.R;
import com.radenmas.voice_control.base.BaseActivity;

public class SplashScreen extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void myCodeHere() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }, 1500);
    }
}