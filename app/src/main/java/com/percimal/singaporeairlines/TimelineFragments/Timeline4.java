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
public class Timeline4 extends Fragment {


    public Timeline4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        int active = getArguments().getInt("active");
        if (active > 0) {
            view = inflater.inflate(R.layout.timeline4, container, false);
            TextView gateText = (TextView) view.findViewById(R.id.timeBody4);
            String gateNumber = getArguments().getString("gate");
            if (gateNumber != null)
                gateText.setText("Gate " + gateNumber);
            else
                gateText.setText("Gate information not yet available.");
            String headerText = getArguments().getString("timeHeader");
            TextView boardingHeader = (TextView) view.findViewById(R.id.timeLeft4);
            boardingHeader.setText(headerText);
        } else {
            view = inflater.inflate(R.layout.timeline4_inactive, container, false);
            int past = getArguments().getInt("past");
            String headerText = getArguments().getString("timeHeader");
            TextView boardingHeader = (TextView) view.findViewById(R.id.timeLeft4);
            if (past > 0)
                boardingHeader.setText("You have boarded the plane. Enjoy your flight!");
            else
                boardingHeader.setText(headerText);
        }

        return view;
    }

}
