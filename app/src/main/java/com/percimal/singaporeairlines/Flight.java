package com.percimal.singaporeairlines;

import java.io.Serializable;
import java.util.Date;

public class Flight implements Serializable {
    Date departure;
    Date arrival;
    String originAirport;
    String originTerminal;
    String destinationAirport;
    String destinationTerminal;
    String flightNumber;
    String marketingAirline;
    String operatingAirline;
    String bookingCode;
    String travelClass;
    String bookingStatus;
    String pnr;
}
