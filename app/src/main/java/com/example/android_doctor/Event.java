package com.example.android_doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event {
    public static ArrayList<Event> eventArrayList=new ArrayList<>();
    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events =new ArrayList<>();
        for (Event event : eventArrayList){
            if (event.getDate().equals(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))))
                events.add(event);
        }
        return events;
    }


    private String name;
    private String date;
    private String time;

    public Event(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
