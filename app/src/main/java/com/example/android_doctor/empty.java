package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
        Boolean bool = sf.getBoolean("Register Bool",false);
        Boolean bool2 = sf.getBoolean("Verify Bool",false);



        if(!bool){
            Intent i=new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            empty.this.finish();

        }
        else if (!bool2){
            Intent j=new Intent(this,otp_verify.class);
            j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(j);
            empty.this.finish();
        }
        else if(bool && bool2){
            Intent k=new Intent(this,home.class);
            k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(k);
            empty.this.finish();
        }

    }
}