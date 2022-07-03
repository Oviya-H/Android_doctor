package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class EventActivity extends AppCompatActivity {

    private EditText EventName;
    private TextView EventDate, EventTime;
    int hour,min;
    int flag=0;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        EventName=findViewById(R.id.eventName);
        EventDate=findViewById(R.id.eventDate);
        EventTime=findViewById(R.id.eventTime);
        db=new DatabaseHelper(this);
        EventDate.setText(formattedDate(calender.selectedDate));
        EventTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,min));;
    }

    public static String formattedDate(LocalDate Date) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return Date.format(formatter);
    }
    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
    }


    public void saveEventAction(View view) {
        String eventName=EventName.getText().toString();
        String eventDate=EventDate.getText().toString();
        String eventTime=EventTime.getText().toString();
        Event newEvent =new Event(eventName,eventDate,eventTime);
        if (!eventName.equals("") && db.insertData(newEvent)){
            Toast.makeText(getApplicationContext(),"Event added",Toast.LENGTH_SHORT).show();
            flag=0;
        }
        else if (eventDate.equals("")){
            EventName.requestFocus();
            EventName.setError("Field cannot be blank");
            flag=1;
        }
        else{
            Toast.makeText(getApplicationContext(),"Event added",Toast.LENGTH_SHORT).show();
        }

        if (flag==0)
            finish();
    }

    public void timePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                hour=selectedHour;
                min=selectedMin;
                EventTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,min));
            }
        };

        TimePickerDialog timePickerDialog= new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,onTimeSetListener,hour,min,true);
        timePickerDialog.show();
    }
}