package com.example.android_doctor;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class prescription extends ListActivity {


    ListView l;
    Button add_pres;
    ArrayList<String> tutorials = null;
    ArrayList<String> Dates_data = null;
    SQLiteDatabase sqLiteDatabase;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);


        add_pres = findViewById(R.id.add_report_home);
        tutorials = new ArrayList<>();
        Dates_data = new ArrayList<>();

        dBhelper = new DBhelper(this,"ImageDB",null,1);
        sqLiteDatabase = dBhelper.getWritableDatabase();

        l = findViewById(android.R.id.list);

        try{
            Cursor cursor = sqLiteDatabase.rawQuery("select * from ImageTable", null);

            for(cursor.moveToFirst(); cursor.moveToNext(); cursor.isAfterLast()) {
                tutorials.add(cursor.getString(0));
                Dates_data.add(cursor.getString(1));
            }


        }catch(Exception e){
            e.printStackTrace();
        }


        inflator_list inf = new inflator_list(this, tutorials, Dates_data);
        l.setAdapter(inf);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Intent
                Intent reportIntent = new Intent(getApplicationContext(), ReportsActivity.class);
                reportIntent.putExtra("Report", tutorials.get(i));
                startActivity(reportIntent);
            }
        });



        add_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pres_intent = new Intent(getApplicationContext(),prescription_new_report.class);
                startActivity(pres_intent);

            }
        });
    }

}