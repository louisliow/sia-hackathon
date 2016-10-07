package com.percimal.singaporeairlines;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements AddFlightDialogFragment.OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private FlightsAdapter flightsAdapter;
    private AddFlightDialogFragment addFlightDialogFragment;
    private DaoSession daoSession;
    private AmadeusService amadeusService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize GreenDao.
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "db.sqlite", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        // Initialize Amadeus service.
        amadeusService = new Retrofit.Builder()
                .baseUrl("https://api.sandbox.amadeus.com/v1.2/")
                .addConverterFactory(new AmadeusConverterFactory())
                .build()
                .create(AmadeusService.class);

        // Set OnClick listener for Foating Action Button.
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFlightDialogFragment.show(getFragmentManager(), "addFlight");
            }
        });

        addFlightDialogFragment = new AddFlightDialogFragment();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Hide FAB on scroll.
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 5)
                    fab.setVisibility(View.GONE);
                else if (dy < -5)
                    fab.setVisibility(View.VISIBLE);
            }
        });

        flightsAdapter = new FlightsAdapter(this, daoSession);
        int numCols = 1;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, numCols);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(numCols, getResources().getDimensionPixelSize(R.dimen.card_spacing), true));
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

    @Override
    public void addFlight(String pnr, String lastName) {
        amadeusService.getTravelRecord(pnr, lastName, getString(R.string.amadeus_api_key)).enqueue(new Callback<TravelRecord>() {
            @Override
            public void onResponse(Call<TravelRecord> call, retrofit2.Response<TravelRecord> response) {
                if (response.isSuccessful()) {
                    TravelRecord travelRecord = response.body();
                    for (Flight flight : travelRecord.flights) {
                        // TODO: Check if flight already exists before adding.
                        daoSession.getFlightDao().insert(flight);
                    }
                    addFlightDialogFragment.addFlightCallback(true);
                    flightsAdapter.updateFlightList();
                } else {
                    addFlightDialogFragment.addFlightCallback(false);
                }
            }

            @Override
            public void onFailure(Call<TravelRecord> call, Throwable t) {
                addFlightDialogFragment.addFlightCallback(false);
            }
        });
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
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
