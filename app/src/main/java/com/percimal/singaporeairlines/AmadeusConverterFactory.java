package com.percimal.singaporeairlines;

import android.annotation.SuppressLint;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class AmadeusConverterFactory extends Converter.Factory {

    @Override
    public Converter responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == TravelRecord.class) {
            return new Converter<ResponseBody, TravelRecord>() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public TravelRecord convert(ResponseBody responseBody) throws IOException {
                    TravelRecord travelRecord = new TravelRecord();
                    travelRecord.flights = new ArrayList<>();
                    JsonParser parser = new JsonParser();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
                    JsonObject jsonObject = parser.parse(responseBody.string()).getAsJsonObject();
                    travelRecord.recordLocator = jsonObject.get("record_locator").getAsString();
                    JsonArray flightTickets = jsonObject.get("reservation").getAsJsonObject().get("flight_tickets").getAsJsonArray();
                    for (JsonElement flightTicketEl : flightTickets) {
                        JsonArray flightBounds = flightTicketEl.getAsJsonObject().get("flight_bounds").getAsJsonArray();
                        for (JsonElement flightBoundEl : flightBounds) {
                            JsonArray flights = flightBoundEl.getAsJsonObject().get("flights").getAsJsonArray();
                            for(JsonElement flightEl : flights) {
                                JsonObject flight = flightEl.getAsJsonObject();
                                Flight f = new Flight();
                                try {
                                    f.departure = dateFormat.parse(flight.get("departs_at").getAsString());
                                    f.arrival = dateFormat.parse(flight.get("arrives_at").getAsString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                f.originAirport = flight.get("origin").getAsJsonObject().get("airport").getAsString();
                                f.originTerminal = flight.get("origin").getAsJsonObject().get("terminal").getAsString();
                                f.destinationAirport = flight.get("destination").getAsJsonObject().get("airport").getAsString();
                                f.destinationTerminal = flight.get("destination").getAsJsonObject().get("terminal").getAsString();
                                f.flightNumber = flight.get("flight_number").getAsString();
                                f.marketingAirline = flight.get("marketing_airline").getAsString();
                                f.operatingAirline = flight.get("operating_airline").getAsString();
                                f.bookingCode = flight.get("booking_info").getAsJsonObject().get("booking_code").getAsString();
                                f.travelClass = flight.get("booking_info").getAsJsonObject().get("travel_class").getAsString();
                                f.bookingStatus = flight.get("booking_info").getAsJsonObject().get("status").getAsString();
                                f.pnr = flight.get("booking_info").getAsJsonObject().get("airline_record_locator").getAsString();
                                travelRecord.flights.add(f);
                            }
                        }
                    }
                    return travelRecord;
                }
            };
        }
        return null;
    }
}
