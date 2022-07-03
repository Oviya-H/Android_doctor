package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_page extends AppCompatActivity {

    EditText Mobile,Password;
    Button Login_btn;
    DBlink DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Mobile = findViewById(R.id.username_login);
        Password = findViewById(R.id.password_login);
        Login_btn = findViewById(R.id.login_btn);
        DB = new DBlink(this);



        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = Mobile.getText().toString();
                String pass = Password.getText().toString();

                if (mobile.trim().equals("") || pass.trim().equals(""))
                    Toast.makeText(getApplicationContext(),"Enter values",Toast.LENGTH_SHORT).show();
                else{
                    Boolean correct = DB.check_mobile_pass(mobile, pass);
                    if(correct){
                        Toast.makeText(getApplicationContext(),"Logged In!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), home.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Username or password", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }
}