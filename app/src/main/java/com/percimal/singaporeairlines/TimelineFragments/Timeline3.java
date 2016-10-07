package com.percimal.singaporeairlines.TimelineFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.percimal.singaporeairlines.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Timeline3 extends Fragment {


    public Timeline3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        int active = getArguments().getInt("active");
        if (active > 0) {
            view = inflater.inflate(R.layout.timeline3, container, false);
            TextView airportInfo = (TextView) view.findViewById(R.id.timeBody3);
            airportInfo.setText(getArguments().getString("airport") + "\nTerminal " + getArguments().getString("terminal"));
        } else {
            view = inflater.inflate(R.layout.timeline3_inactive, container, false);
        }
        String headerText = getArguments().getString("timeHeader");
        TextView checkinHeader = (TextView) view.findViewById(R.id.timeLeft3);
        checkinHeader.setText(headerText);

        return view;
    }

}
