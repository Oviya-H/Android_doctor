package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username,password,retype;
    Button register, login, verify;
    DBlink DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_signup);
        password = findViewById(R.id.password_signup);
        retype = findViewById(R.id.repassword_signup);
        register = findViewById(R.id.register_signup);
        verify = findViewById(R.id.Verify_signup);
        login = findViewById(R.id.login_signup);
        DB = new DBlink(this);

        SharedPreferences sf2=getSharedPreferences("Phone Number", Context.MODE_PRIVATE);
        username.setText(sf2.getString("Phone", ""));


        SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sf.edit();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Verify_intent = new Intent(getApplicationContext(),otp_verify.class);
                startActivity(Verify_intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mobile = username.getText().toString();
                String pass = password.getText().toString();
                String repass = retype.getText().toString();

                if (mobile.trim().equals("") || pass.trim().equals("") || repass.trim().equals(""))
                    Toast.makeText(getApplicationContext(),"fill all values",Toast.LENGTH_SHORT).show();


                else{

                    if (pass.equals(repass)){
                        Boolean checkuser = DB.check_mobile(mobile);
                        if(!checkuser){
                            Boolean insert = DB.insert_value(mobile,pass);
                            if(insert){
                                Toast.makeText(getApplicationContext(),"Registered Succesfully!",Toast.LENGTH_SHORT).show();
                                SharedPreferences sf2=getSharedPreferences("Phone Number", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit2=sf2.edit();
                                edit2.putString("Phone", mobile);

                                edit2.apply();

                                edit.putBoolean("Register Bool",true);

                                edit.apply();
                                Intent home_intent = new Intent(getApplicationContext(),otp_verify.class);
                                startActivity(home_intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Registered Failed, Try again",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"User already Exist!!",Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Password Not match",Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login_page.class);
                startActivity(intent);
            }
        });
    }


}