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
        } else {
            view = inflater.inflate(R.layout.timeline2_inactive, container, false);
        }
        String headerText = getArguments().getString("timeHeader");
        TextView reachingHeader = (TextView) view.findViewById(R.id.timeLeft2);
        reachingHeader.setText(headerText);


        return view;
    }

}
