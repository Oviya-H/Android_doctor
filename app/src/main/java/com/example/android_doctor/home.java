package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class home extends AppCompatActivity {

    Button Calender, Prescription, Account;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Calender = findViewById(R.id.menu_appoinments);
        Prescription = findViewById(R.id.menu_prescription);
        Account = findViewById(R.id.menu_account);
        text = findViewById(R.id.welcome_home);

        SharedPreferences sf=getSharedPreferences("Account Details", Context.MODE_PRIVATE);
        String str = sf.getString("Name", "");

        if(str.equals("")){
            text.setText("Hello !!!");

        }else{
            text.setText("Welcome "+str+" !!");
        }


        Calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), calender.class);
                startActivity(intent);
            }
        });
        Prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), prescription.class);
                startActivity(intent);


            }
        });
        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
            }
        });

    }
}