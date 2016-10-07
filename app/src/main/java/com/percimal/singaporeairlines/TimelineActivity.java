package com.percimal.singaporeairlines;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.percimal.singaporeairlines.TimelineFragments.Timeline2;
import com.percimal.singaporeairlines.TimelineFragments.Timeline3;
import com.percimal.singaporeairlines.TimelineFragments.Timeline4;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.w3c.dom.Text;

public class TimelineActivity extends AppCompatActivity {

    static int minsForReaching = 20;
    static int minsForCheckin = 180;
    static int minsForBoarding = 45;
    private DateTime now;
    private DateTime depatureTime, boardingTime, checkinTime, reachTime, leaveTime;

    private Flight flight;
    private int step;
    private String airport, terminal, gate;
    private DateTimeFormatter timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        JodaTimeAndroid.init(this);
        TextView startHeader = (TextView) findViewById(R.id.startText);

        if (flight != null) {
            depatureTime = new DateTime(flight.departure);
            startHeader.setText("Your flight from " + flight.originAirport.toUpperCase() + " to " +
                    flight.destinationAirport.toUpperCase() + " is today!");
            airport = flight.originAirport;
            terminal = flight.originTerminal;
            gate = "E2";
        } else { //dummy data
            depatureTime = new DateTime();
            depatureTime = depatureTime.plusHours(6);
            startHeader.setText("Your flight from SIN to ICN is today!");
            airport = "Changi Airport";
            terminal = "2";
            gate = "A13";
        }
        boardingTime = depatureTime.minusMinutes(minsForBoarding);
        checkinTime = boardingTime.minusMinutes(minsForCheckin);
        reachTime = checkinTime.minusMinutes(minsForReaching);
        now = new DateTime();
        timeFormat = new DateTimeFormatterBuilder().appendPattern("H:mm").toFormatter();
        TextView takeOffText = (TextView) findViewById(R.id.takeoffText);
        takeOffText.setText("Flight departs at " + depatureTime.toString(timeFormat));

        // Calculate current phase here:
        step = 1;
        drawFragments(true);
    }

    private void drawFragments(boolean firstDraw) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction;
        Bundle bundle;

        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment2 = new Timeline2();
        bundle = new Bundle();
        bundle.putString("timeHeader", "Reach airport by " + reachTime.toString(timeFormat));
        bundle.putInt("active", (step == 1) ? 1 : 0);
        fragment2.setArguments(bundle);
        if (firstDraw)
            fragmentTransaction.add(R.id.phase2Holder, fragment2);
        else
            fragmentTransaction.replace(R.id.phase2Holder, fragment2);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment3 = new Timeline3();
        bundle = new Bundle();
        bundle.putString("timeHeader", "Check in by " + checkinTime.toString(timeFormat));
        bundle.putString("airport", airport);
        bundle.putString("terminal", terminal);
        bundle.putInt("active", (step == 2) ? 1 : 0);
        fragment3.setArguments(bundle);
        if (firstDraw)
            fragmentTransaction.add(R.id.phase3Holder, fragment3);
        else
            fragmentTransaction.replace(R.id.phase3Holder, fragment3);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment4 = new Timeline4();
        bundle = new Bundle();
        bundle.putString("timeHeader", "Boarding begins at " + boardingTime.toString(timeFormat));
        bundle.putString("gate", gate);
        bundle.putInt("active", (step == 3) ? 1 : 0);
        fragment4.setArguments(bundle);
        if (firstDraw)
            fragmentTransaction.add(R.id.phase4Holder, fragment4);
        else
            fragmentTransaction.replace(R.id.phase4Holder, fragment4);
        fragmentTransaction.commit();
    }

    public void startGMaps(View view) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Changi+Airport+Terminal+2,+Singapore");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void timeClick2(View view) {
        step = 1;
        drawFragments(false);
    }
    public void timeClick3(View view) {
        step = 2;
        drawFragments(false);
    }
    public void timeClick4(View view) {
        step = 3;
        drawFragments(false);
    }
}
