package com.example.android_doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class calender extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthText;
    private RecyclerView calendarRecycler;
    public static LocalDate selectedDate;
    DatabaseHelper databaseHelper;
    ArrayList<Event> arrayList;
    ArrayList<Event> dailyEvents= null;
    private ListView eventListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calendarRecycler=findViewById(R.id.calendarRecyclerView);
        monthText=findViewById(R.id.monthTV);
        eventListview=findViewById(R.id.eventList);
        selectedDate=LocalDate.now();
        arrayList=new ArrayList<>();
        databaseHelper=new DatabaseHelper(this);
        setMonthView();
    }

    private void setMonthView() {

        monthText.setText(monthYear(selectedDate));
        ArrayList<LocalDate> daysinMonth= daysinMonthArray(selectedDate);
        CalendarAdapter calendarAdapter=new CalendarAdapter(daysinMonth,this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),7);
        calendarRecycler.setLayoutManager(layoutManager);
        calendarRecycler.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    public static ArrayList<LocalDate> daysinMonthArray(LocalDate selectedDate) {
        ArrayList<LocalDate> daysinMonthArray=new ArrayList<>();
        YearMonth yearMonth=YearMonth.from(selectedDate);
        int daysinMonth=yearMonth.lengthOfMonth();
        LocalDate firstOfMonth=selectedDate.withDayOfMonth(1);
        int dayofWeek=firstOfMonth.getDayOfWeek().getValue();
        for(int i=1;i<=42;i++)
        {
            if (i<=dayofWeek || i>daysinMonth+dayofWeek)
            {
                daysinMonthArray.add(null);
            }
            else
            {
                daysinMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i - dayofWeek));
            }
        }
        return daysinMonthArray;
    }

    public static String monthYear(LocalDate date){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void nextMonth(View view) {
        selectedDate=selectedDate.plusMonths(1);
        setMonthView();
    }

    public void prevMonth(View view) {
        selectedDate=selectedDate.minusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate dayText) {
        if (dayText!=null){
            selectedDate=dayText;
            setMonthView();
        }
    }
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        Cursor cursor=databaseHelper.getAllData();
        Event.eventArrayList.clear();

        if (cursor.getCount()!=0){
            while (cursor.moveToNext()){
                String name=cursor.getString(1);
                String date= cursor.getString(2);
                String time=cursor.getString(3);
                Event newEvent=new Event(name,date,time);
                Event.eventArrayList.add(newEvent);
            }
        }
        dailyEvents = Event.eventsForDate(selectedDate);
        EventAdapter eventAdapter=new EventAdapter(getApplicationContext(),dailyEvents);
        eventListview.setAdapter(eventAdapter);
    }
    public void newEventAction(View view){
        startActivity(new Intent(this,EventActivity.class));
    }
}