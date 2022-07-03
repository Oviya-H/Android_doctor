package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class otp_verify extends AppCompatActivity implements SMSlistener{

    MyReciever myReceiver = new MyReciever();
    EditText Otp;
    Button submit_btn,send_btn;
    int min = 1000;
    int max = 9999;
    Boolean bool_otp;
    Boolean bool2_otp;

    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
    String message = String.format("your otp is %d",random_int);
    EditText phone_no;
    public static final String OTP_REGEX = "[0-9]{1,6}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);
        MyReciever.bindListener(this);
        Otp = findViewById(R.id.otp_ed);
        send_btn = findViewById(R.id.send);
        phone_no = findViewById(R.id.number);

        SharedPreferences sf2=getSharedPreferences("Phone Number", Context.MODE_PRIVATE);
        phone_no.setText(sf2.getString("Phone", ""));

        SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
        bool_otp = sf.getBoolean("Register Bool",false);
        bool2_otp = sf.getBoolean("Verify Bool",false);

        submit_btn = findViewById(R.id.submit);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = Otp.getText().toString();
                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText); String otp = "XXXXX";
                while (matcher.find()) {
                    otp = matcher.group();
                }

                if (random_int == Integer.parseInt(otp)){
                    SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=sf.edit();

                    edit.putBoolean("Verify Bool",true);
                    edit.apply();

                    if(!bool_otp){
                        Intent next = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(next);
                    }else{
                        Intent next = new Intent(getApplicationContext(), home.class);
                        startActivity(next);
                    }



                }
            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senderno = phone_no.getText().toString();
                send_sms(senderno);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter ("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(myReceiver);
    }

    @Override
    public void messageReceived(String messageText, String senderno) {
        Pattern pattern = Pattern.compile(OTP_REGEX);
        Matcher matcher = pattern.matcher(messageText); String otp = "XXXXX";
        while (matcher.find()) {
            otp = matcher.group();
        }
        Toast.makeText(this,"OTP Received is: "+ otp ,Toast.LENGTH_LONG).show();

        Otp.setText(otp);

        if (random_int == Integer.parseInt(otp)){
            Toast.makeText(this,"Number Verified!!!!" ,Toast.LENGTH_LONG).show();
            SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit=sf.edit();

            edit.putBoolean("Verify Bool",true);
            edit.apply();

            if(!bool_otp){
                Intent next = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(next);
            }else{
                Intent next = new Intent(getApplicationContext(), home.class);
                startActivity(next);
            }



        }


    }

    private void send_sms(String number) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        }


        String phoneNo = "+91"+number;
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getApplicationContext(),"Sms send to "+phoneNo,Toast.LENGTH_LONG).show();

    }
}