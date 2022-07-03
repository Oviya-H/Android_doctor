package com.example.android_doctor;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class top_layout_fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_top_layout_fragment, container, false);
        Button Menu = view.findViewById(R.id.menu);
        Dialog menu_pop;
        menu_pop = new Dialog(getActivity());
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_pop.setContentView(R.layout.activity_popup);
                menu_pop.show();
            }
        });

        return view;
    }


}