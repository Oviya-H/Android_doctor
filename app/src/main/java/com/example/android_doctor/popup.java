package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class popup extends AppCompatActivity {

    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);


    }

    public void backTOhome(View view) {
        Intent home_intent = new Intent(getApplicationContext(), home.class);
        startActivity(home_intent);
    }

    public void Logout_page(View view) {
        System.exit(0);
    }
}