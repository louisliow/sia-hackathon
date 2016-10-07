package com.percimal.singaporeairlines.TimelineFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.percimal.singaporeairlines.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Timeline2 extends Fragment {


    public Timeline2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        int active = getArguments().getInt("active");
        if (active > 0) {
            view = inflater.inflate(R.layout.timeline2, container, false);
            String headerText = getArguments().getString("timeHeader");
            TextView reachingHeader = (TextView) view.findViewById(R.id.timeLeft2);
            reachingHeader.setText(headerText);
            String airport = getArguments().getString("airport");
            String terminal = getArguments().getString("terminal");
            TextView reachingBody = (TextView) view.findViewById(R.id.timeBody2);
            reachingBody.setText("Your drop-off point will be at terminal " + terminal + " of " + airport +
                    ". Please be reminded to bring your passport and all essential items.");
        } else {
            view = inflater.inflate(R.layout.timeline2_inactive, container, false);
            int past = getArguments().getInt("past");
            String headerText = getArguments().getString("timeHeader");
            TextView reachingHeader = (TextView) view.findViewById(R.id.timeLeft2);
            if (past > 0)
                reachingHeader.setText("You have reached the airport");
            else
                reachingHeader.setText(headerText);
        }


        return view;
    }

}
