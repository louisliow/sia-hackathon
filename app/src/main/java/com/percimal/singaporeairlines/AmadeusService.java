package com.percimal.singaporeairlines;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AmadeusService {
    @GET("travel-record/{record_locator}?env=TEST")
    Call<TravelRecord> getTravelRecord(@Path("record_locator") String recordLocator, @Query("last_name") String lastName, @Query("apikey") String apikey);
}
