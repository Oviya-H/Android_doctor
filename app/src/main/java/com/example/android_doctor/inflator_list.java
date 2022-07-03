package com.example.android_doctor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class inflator_list extends ArrayAdapter {

    private ArrayList<String> Title;
    private ArrayList<String> Date;
    private Activity context;

    public inflator_list(Activity context, ArrayList<String> Titles, ArrayList<String> Dates) {
        super(context, R.layout.custom_list, Titles);
        this.context = context;
        this.Title = Titles;
        this.Date = Dates;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
            row = inflater.inflate(R.layout.custom_list, null, true);

        TextView textViewCountry = row.findViewById(R.id.Title_cl);
        TextView textViewCapital =  row.findViewById(R.id.Date_cl);

        textViewCountry.setText(Title.get(position));
        textViewCapital.setText(Date.get(position));
        return row;
    }
}
