package com.percimal.singaporeairlines;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PorterService {
    @POST("porter/request")
    Call<Void> createPorterRequest(@Body CreatePortalRequestBody body);

    @GET("porter/request/{rideId}")
    Call<Void> getPorterRequest(@Path("rideId") String rideId);
}

class CreatePortalRequestBody {
    String uberAccessToken;
    String flightNumber;
    String airport;
    String terminal;
    String travelClass;
    public CreatePortalRequestBody(String uberAccessToken, String flightNumber, String airport, String terminal, String travelClass) {
        this.uberAccessToken = uberAccessToken;
        this.flightNumber = flightNumber;
        this.airport = airport;
        this.terminal = terminal;
        this.travelClass = travelClass;
    }
}