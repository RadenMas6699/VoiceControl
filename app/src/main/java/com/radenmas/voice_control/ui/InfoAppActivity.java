package com.radenmas.voice_control.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageButton;
import android.widget.TextView;

import com.radenmas.voice_control.R;
import com.radenmas.voice_control.base.BaseActivity;

public class InfoAppActivity extends BaseActivity {
    private TextView descApp, versionApp;
    private ImageButton imgBack;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_info_app;
    }

    @Override
    protected void myCodeHere() {
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });

        PackageManager manager = getApplication().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;

        versionApp = findViewById(R.id.version_app);
        versionApp.setText("Version "+version);

        descApp = findViewById(R.id.desc_app);
        descApp.setMovementMethod(new ScrollingMovementMethod());
    }
}
