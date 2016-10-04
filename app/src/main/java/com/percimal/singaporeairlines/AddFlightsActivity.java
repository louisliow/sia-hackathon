package com.percimal.singaporeairlines;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.percimal.singaporeairlines.data.Flight;
import com.percimal.singaporeairlines.data.FlightDao;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AddFlightsActivity extends Activity {

    AmadeusService amadeusService;
    private FlightDao flightDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flights);

        flightDao = PercimalApplication.getInstance()
                .getDaoSession()
                .getFlightDao();

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    HttpUrl url = chain.request().url().newBuilder()
                            .addQueryParameter("apikey", getString(R.string.amadeus_api_key))
                            .build();
                    Request request = chain.request().newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.sandbox.amadeus.com/v1.2/")
                .addConverterFactory(new AmadeusConverterFactory())
                .client(client)
                .build();
        amadeusService = retrofit.create(AmadeusService.class);
    }

    public void addFlights(View view) {
        EditText pnrInput = (EditText) findViewById(R.id.pnr);
        EditText lastNameInput = (EditText) findViewById(R.id.lastName);
        String pnr = pnrInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        Boolean hasError = false;
        if(pnr.equals("")) {
            pnrInput.setError("Booking reference number is required");
            hasError = true;
        }
        if (lastName.equals("")) {
            lastNameInput.setError("Last name is required");
            hasError = true;
        }
        if (hasError) {
            return;
        }

        // TODO: Show loading indicator.
        Call<TravelRecord> call = amadeusService.getTravelRecord(pnr, lastName);
        call.enqueue(new Callback<TravelRecord>() {
            @Override
            public void onResponse(Call<TravelRecord> call, retrofit2.Response<TravelRecord> response) {
                if (response.isSuccessful()) {
                    TravelRecord travelRecord = response.body();
                    for (Flight flight : travelRecord.flights) {
                        // TODO: Persist flights to SQLite.
                        flightDao.insert(flight);
                    }
                } else {
                    // TODO: Display error message for no record found.
                }
                // TODO: Hide loading indicator.
            }

            @Override
            public void onFailure(Call<TravelRecord> call, Throwable t) {
                // TODO: Display error message to indicate network exception.
                // TODO: Hide loading indicator.
            }
        });
    }
}
