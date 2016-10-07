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
            String airport = getArguments().getString("airport");
            String terminal = getArguments().getString("terminal");
            airportInfo.setText(airport + ", Terminal " + terminal);
            String headerText = getArguments().getString("timeHeader");
            TextView checkinHeader = (TextView) view.findViewById(R.id.timeLeft3);
            checkinHeader.setText(headerText);

            TextView checkinBody = (TextView) view.findViewById(R.id.timeBody3_2);
            checkinBody.setText("Your baggage allowance for this trip is 2 pieces of 32kg each.\n\n" +
                "Liquid products must be in containers of 100ml or less (broadly equivalent to 100 grams or less) " +
                    "and carried together in a transparent, resealable plastic bag. \n\n" +
                "The bag's sealed area must add up to no more than 80cm.\n\n" +
                "Only one bag is allowed for each passenger, with exceptions for " +
                    "carers who may carry the bags of the people in their care, including children");
        } else {
            view = inflater.inflate(R.layout.timeline3_inactive, container, false);
            int past = getArguments().getInt("past");
            String headerText = getArguments().getString("timeHeader");
            TextView checkinHeader = (TextView) view.findViewById(R.id.timeLeft3);
            if (past > 0)
                checkinHeader.setText("You have checked in");
            else
                checkinHeader.setText(headerText);
        }

        return view;
    }

}
