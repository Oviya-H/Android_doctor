package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ReportsActivity extends AppCompatActivity {

    TextView tv, date;
    ImageView img;
    SQLiteDatabase sqLiteDatabase;
    DBhelper dBhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        img = findViewById(R.id.Image_report);
        tv = findViewById(R.id.out);
        date = findViewById(R.id.date_report);
        Bundle b= getIntent().getExtras();
        String output = b.getString("Report");

        dBhelper = new DBhelper(this,"ImageDB",null,1);
        sqLiteDatabase = dBhelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from ImageTable where Title=?", new String[] {output});

        cursor.moveToFirst();
        String date_text = cursor.getString(1);
        date.setText(date_text);

        tv.setText(cursor.getString(0));

        cursor.moveToFirst();
        File f = new File(cursor.getString(2));
        img.setImageURI(Uri.fromFile(f));


        cursor.close();





    }
}