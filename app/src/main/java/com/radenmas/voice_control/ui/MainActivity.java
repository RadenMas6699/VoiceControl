package com.radenmas.voice_control.ui;

import android.widget.ImageButton;

import com.radenmas.voice_control.R;
import com.radenmas.voice_control.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private ImageButton imgBtnVoice;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void myCodeHere() {

        initView();
        onClick();
    }

    private void onClick() {
        imgBtnVoice.setOnClickListener(view -> {
            toastS("Voice");
        });
    }

    private void initView() {
        imgBtnVoice = findViewById(R.id.imgBtnVoice);
    }


}