package com.radenmas.voice_control.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutResource();

    protected abstract void myCodeHere();

    public FirebaseDatabase database;
    public DatabaseReference dbReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        database = FirebaseDatabase.getInstance();
        dbReff = database.getReference();

        myCodeHere();
    }

    protected void toastS(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    protected void toastL(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }
}
