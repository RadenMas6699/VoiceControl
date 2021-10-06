package com.radenmas.voice_control.ui;

import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.radenmas.voice_control.R;
import com.radenmas.voice_control.base.BaseActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private ImageView imgAC, imgLamp1, imgFan, imgRefrigerator, imgLamp2,
            stateAC, stateLamp1, stateFan, stateRefrigerator, stateLamp2;
    private ImageButton imgBtnVoice, switchAC, switchLamp1, switchFan, switchRefrigerator, switchLamp2;
    int ac, lamp1, fan, refrigerator, lamp2;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void myCodeHere() {
        initView();
        onClick();
        cekStatus();
    }

    private void cekStatus() {
        dbReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String strAC = snapshot.child("ac").getValue().toString();
                String strLamp1 = snapshot.child("lampu_1").getValue().toString();
                String strFan = snapshot.child("fan").getValue().toString();
                String strKulkas = snapshot.child("refrigerator").getValue().toString();
                String strLamp2 = snapshot.child("lampu_2").getValue().toString();

                ac = Integer.parseInt(strAC);
                lamp1 = Integer.parseInt(strLamp1);
                fan = Integer.parseInt(strFan);
                refrigerator = Integer.parseInt(strKulkas);
                lamp2 = Integer.parseInt(strLamp2);

                if (ac == 1) {
                    ac = 0;
                    deviceOn(imgAC, switchAC, stateAC);
                } else {
                    ac = 1;
                    deviceOff(imgAC, switchAC, stateAC);
                }
                if (lamp1 == 1) {
                    lamp1 = 0;
                    deviceOn(imgLamp1, switchLamp1, stateLamp1);
                } else {
                    lamp1 = 1;
                    deviceOff(imgLamp1, switchLamp1, stateLamp1);
                }
                if (fan == 1) {
                    fan = 0;
                    deviceOn(imgFan, switchFan, stateFan);
                } else {
                    fan = 1;
                    deviceOff(imgFan, switchFan, stateFan);
                }
                if (refrigerator == 1) {
                    refrigerator = 0;
                    deviceOn(imgRefrigerator, switchRefrigerator, stateRefrigerator);
                } else {
                    refrigerator = 1;
                    deviceOff(imgRefrigerator, switchRefrigerator, stateRefrigerator);
                }
                if (lamp2 == 1) {
                    lamp2 = 0;
                    deviceOn(imgLamp2, switchLamp2, stateLamp2);
                } else {
                    lamp2 = 1;
                    deviceOff(imgLamp2, switchLamp2, stateLamp2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deviceOff(ImageView imgIcon, ImageButton imagePower, ImageView imageState) {
        imgIcon.setColorFilter(Color.argb(255, 222, 222, 222));
        imagePower.setImageResource(R.drawable.ic_power_off);
        imageState.setVisibility(View.INVISIBLE);
    }

    private void deviceOn(ImageView imgIcon, ImageButton imagePower, ImageView imageState) {
        imgIcon.setColorFilter(null);
        imagePower.setImageResource(R.drawable.ic_power_on);
        imageState.setVisibility(View.VISIBLE);
    }

    private void onClick() {
        imgBtnVoice.setOnClickListener(view -> {
            startVoiceInput();
        });
        switchAC.setOnClickListener(view -> {
            if (ac == 1) {
                ac = 0;
                deviceOn(imgAC, switchAC, stateAC);
                dbReff.child("ac").setValue(1);
            } else {
                ac = 1;
                deviceOff(imgAC, switchAC, stateAC);
                dbReff.child("ac").setValue(0);
            }
        });
        switchLamp1.setOnClickListener(view -> {
            if (lamp1 == 1) {
                lamp1 = 0;
                deviceOn(imgLamp1, switchLamp1, stateLamp1);
                dbReff.child("lampu_1").setValue(1);
            } else {
                lamp1 = 1;
                deviceOff(imgLamp1, switchLamp1, stateLamp1);
                dbReff.child("lampu_1").setValue(0);
            }
        });
        switchFan.setOnClickListener(view -> {
            if (fan == 1) {
                fan = 0;
                deviceOn(imgFan, switchFan, stateFan);
                dbReff.child("fan").setValue(1);
            } else {
                fan = 1;
                deviceOff(imgFan, switchFan, stateFan);
                dbReff.child("fan").setValue(0);
            }
        });
        switchRefrigerator.setOnClickListener(view -> {
            if (refrigerator == 1) {
                refrigerator = 0;
                deviceOn(imgRefrigerator, switchRefrigerator, stateRefrigerator);
                dbReff.child("refrigerator").setValue(1);
            } else {
                refrigerator = 1;
                deviceOff(imgRefrigerator, switchRefrigerator, stateRefrigerator);
                dbReff.child("refrigerator").setValue(0);
            }
        });
        switchLamp2.setOnClickListener(view -> {
            if (lamp2 == 1) {
                lamp2 = 0;
                deviceOn(imgLamp2, switchLamp2, stateLamp2);
                dbReff.child("lampu_2").setValue(1);
            } else {
                lamp2 = 1;
                deviceOff(imgLamp2, switchLamp2, stateLamp2);
                dbReff.child("lampu_2").setValue(0);
            }
        });
    }

    //perintah suara
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice Control");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (arrayList.get(0).equalsIgnoreCase("nyalakan ac")) {
                dbReff.child("ac").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan ac")) {
                dbReff.child("ac").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("ac nyala")) {
                dbReff.child("ac").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("ac mati")) {
                dbReff.child("ac").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("nyalakan lampu satu")) {
                dbReff.child("lampu_1").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan lampu satu")) {
                dbReff.child("lampu_1").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu satu nyala")) {
                dbReff.child("lampu_1").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu satu mati")) {
                dbReff.child("lampu_1").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("nyalakan lampu 1")) {
                dbReff.child("lampu_1").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan lampu 1")) {
                dbReff.child("lampu_1").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu 1 nyala")) {
                dbReff.child("lampu_1").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu 1 mati")) {
                dbReff.child("lampu_1").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("nyalakan kipas")) {
                dbReff.child("fan").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan kipas")) {
                dbReff.child("fan").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("kipas nyala")) {
                dbReff.child("fan").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("kipas mati")) {
                dbReff.child("fan").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("nyalakan kulkas")) {
                dbReff.child("refrigerator").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan kulkas")) {
                dbReff.child("refrigerator").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("kulkas nyala")) {
                dbReff.child("refrigerator").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("kulkas mati")) {
                dbReff.child("refrigerator").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("nyalakan lampu dua")) {
                dbReff.child("lampu_2").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan lampu dua")) {
                dbReff.child("lampu_2").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu dua nyala")) {
                dbReff.child("lampu_2").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu dua mati")) {
                dbReff.child("lampu_2").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("nyalakan lampu 2")) {
                dbReff.child("lampu_2").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("matikan lampu 2")) {
                dbReff.child("lampu_2").setValue(0);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu 2 nyala")) {
                dbReff.child("lampu_2").setValue(1);
            } else if (arrayList.get(0).equalsIgnoreCase("lampu 2 mati")) {
                dbReff.child("lampu_2").setValue(0);
            }
        }
    }

    private void initView() {
        imgAC = findViewById(R.id.img_ac);
        imgLamp1 = findViewById(R.id.img_lamp1);
        imgFan = findViewById(R.id.img_fan);
        imgRefrigerator = findViewById(R.id.img_refrigerator);
        imgLamp2 = findViewById(R.id.img_lamp2);
        imgBtnVoice = findViewById(R.id.img_btn_voice);
        switchAC = findViewById(R.id.switch_ac);
        switchLamp1 = findViewById(R.id.switch_lamp1);
        switchFan = findViewById(R.id.switch_fan);
        switchRefrigerator = findViewById(R.id.switch_refrigerator);
        switchLamp2 = findViewById(R.id.switch_lamp2);
        stateAC = findViewById(R.id.state_ac);
        stateLamp1 = findViewById(R.id.state_lamp1);
        stateFan = findViewById(R.id.state_fan);
        stateRefrigerator = findViewById(R.id.state_refrigerator);
        stateLamp2 = findViewById(R.id.state_lamp2);
    }
}