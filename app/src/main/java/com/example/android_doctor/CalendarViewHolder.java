package com.example.android_doctor;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ArrayList<LocalDate> daysofMonth;
    public final View parentView;
    public final TextView dayofMonth;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener,ArrayList<LocalDate> daysofMonth) {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayofMonth=itemView.findViewById(R.id.cellDay);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.daysofMonth=daysofMonth;
    }



    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(),daysofMonth.get(getAdapterPosition()) );
    }
}
