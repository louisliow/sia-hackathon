package com.percimal.singaporeairlines;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class FlightDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Flight flight = (Flight) getIntent().getSerializableExtra("flight");
        ((CollapsingToolbarLayout)findViewById(R.id.toolbar_layout)).setTitle(flight.originAirport + " - " + flight.destinationAirport);
        ((ImageView) findViewById(R.id.cover_image)).setImageResource(getResources().getIdentifier(flight.destinationAirport.toLowerCase(), "drawable", getPackageName()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UberRequestActivity.class);
                intent.putExtra("flight", flight);
                startActivity(intent);
            }
        });

    }

}
