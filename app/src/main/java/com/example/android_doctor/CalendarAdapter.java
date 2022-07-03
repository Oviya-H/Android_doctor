package com.example.android_doctor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<LocalDate> daysofMonth;
    private final OnItemListener onItemListener;
    public CalendarAdapter(ArrayList<LocalDate> daysofMonth,OnItemListener onItemListener)
    {
        this.daysofMonth = daysofMonth;
        this.onItemListener=onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
        layoutParams.height=(int) (parent.getHeight()*0.166);
        return new CalendarViewHolder(view, onItemListener, daysofMonth);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate localDate = daysofMonth.get(position);
        if(localDate==null)
            holder.dayofMonth.setText("");
        else
        {
            holder.dayofMonth.setText(String.valueOf(localDate.getDayOfMonth()));
            if(localDate.equals(calender.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
        }

    }

    @Override
    public int getItemCount() {
        return daysofMonth.size();
    }
    public interface OnItemListener{
        void onItemClick(int position, LocalDate date);
    }
}

