package com.percimal.singaporeairlines;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlightsAdapter flightsAdapter;
    private List<Flight> flightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFlightsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // TODO: Replace mock data with data from SQLite.
        flightList = new ArrayList<>();
        Flight f1 = new Flight();
        f1.originAirport = "SIN";
        f1.destinationAirport = "ICN";
        flightList.add(f1);
        Flight f2 = new Flight();
        f2.originAirport = "ICN";
        f2.destinationAirport = "SFO";
        flightList.add(f2);
        Flight f3 = new Flight();
        f3.originAirport = "SFO";
        f3.destinationAirport = "SIN";
        flightList.add(f3);
        Flight f4 = new Flight();
        f4.originAirport = "SIN";
        f4.destinationAirport = "HKG";
        flightList.add(f4);
        Flight f5 = new Flight();
        f5.originAirport = "HKG";
        f5.destinationAirport = "SIN";
        flightList.add(f5);
        Flight f6 = new Flight();
        f6.originAirport = "SIN";
        f6.destinationAirport = "JFK";
        flightList.add(f6);
        Flight f7 = new Flight();
        f7.originAirport = "JFK";
        f7.destinationAirport = "SIN";
        flightList.add(f7);
        Flight f8 = new Flight();
        f8.originAirport = "SIN";
        f8.destinationAirport = "LHR";
        flightList.add(f8);

        flightsAdapter = new FlightsAdapter(this, flightList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 15, true));
        recyclerView.setAdapter(flightsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spacing, getResources().getDisplayMetrics()));
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }

    }
}
