package com.example.android_doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Account extends AppCompatActivity {
    Button add_address, save, Reset;
    EditText address;
    EditText email, name, phone;
    FusedLocationProviderClient fusedLocationProviderClient;

    String[] tags = {"A+", "A-", "B+", "B-", "O-", "O+", "AB+", "AB-"};
    AutoCompleteTextView atv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        name = findViewById(R.id.name_account);
        phone = findViewById(R.id.phone_account);
        save = findViewById(R.id.submit_account);
        email = findViewById(R.id.email_account);
        add_address = findViewById(R.id.address_btn_account);
        address = findViewById(R.id.address_account);
        Reset = findViewById(R.id.reset_account);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        atv2 = findViewById(R.id.bloodgroup_account);

        SharedPreferences sf=getSharedPreferences("Account Details", Context.MODE_PRIVATE);
        String name_sp = sf.getString("Name", "");
        String email_sp = sf.getString("Email", "");
        String phone_sp = sf.getString("Phone", "");
        String address_sp = sf.getString("Address", "");
        String blood_sp = sf.getString("Blood_gp", "");
        name.setText(name_sp);
        phone.setText(phone_sp);
        atv2.setText(blood_sp);
        email.setText(email_sp);
        address.setText(address_sp);

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                phone.setText("");
                atv2.setText("");
                email.setText("");
                address.setText("");
                SharedPreferences sf=getSharedPreferences("Account Details", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit=sf.edit();

                edit.putString("Name", "");
                edit.putString("Email", "");
                edit.putString("Phone", "");
                edit.putString("Address", "");
                edit.putString("Blood_gp", "");
                edit.apply();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, tags);
        atv2.setAdapter(adapter);
        atv2.setThreshold(1);



        atv2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                atv2.showDropDown();
                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email_text = email.getText().toString();
                String Name_text = name.getText().toString();
                String Phone_text = phone.getText().toString();
                String Address_text = address.getText().toString();
                String Blood_gr_text = atv2.getText().toString();

                boolean check;
                check = isValid(Email_text);

                if(!check){
                    Toast.makeText(getApplicationContext(),"Enter a Valid Email", Toast.LENGTH_SHORT).show();

                }
                if(Name_text.trim().equals("") ||Email_text.trim().equals("") ||Phone_text.trim().equals("") ||Address_text.trim().equals("") ||Blood_gr_text.trim().equals("") ){
                    Toast.makeText(getApplicationContext(),"Fill all the values", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Account saved successfully !!", Toast.LENGTH_SHORT).show();
                        SharedPreferences sf=getSharedPreferences("Account Details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=sf.edit();

                        edit.putString("Name", Name_text);
                        edit.putString("Email", Email_text);
                        edit.putString("Phone", Phone_text);
                        edit.putString("Address", Address_text);
                        edit.putString("Blood_gp", Blood_gr_text);
                        edit.apply();

                }

            }
        });
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(Account.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getlol();
                } else {
                    Toast.makeText(getApplicationContext(), "Please give access", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(Account.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }

        });

    }

    private void getlol() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(Account.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        address.setText(addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}