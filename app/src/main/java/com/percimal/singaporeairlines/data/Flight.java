package com.percimal.singaporeairlines.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Peter on 5/10/2016.
 */

@Entity
public class Flight {
    @Id
    private long id;

    private String originAirport;
    private String originTerminal;
    private String destinationAirport;
    private String destinationTerminal;
    private String flightNumber;
    private String marketingAirline;
    private String operatingAirline;
    private String bookingCode;
    private String travelClass;
    private String bookingStatus;
    private String pnr;

    @Generated(hash = 1730761550)
    public Flight(long id, String originAirport, String originTerminal,
            String destinationAirport, String destinationTerminal,
            String flightNumber, String marketingAirline, String operatingAirline,
            String bookingCode, String travelClass, String bookingStatus,
            String pnr) {
        this.id = id;
        this.originAirport = originAirport;
        this.originTerminal = originTerminal;
        this.destinationAirport = destinationAirport;
        this.destinationTerminal = destinationTerminal;
        this.flightNumber = flightNumber;
        this.marketingAirline = marketingAirline;
        this.operatingAirline = operatingAirline;
        this.bookingCode = bookingCode;
        this.travelClass = travelClass;
        this.bookingStatus = bookingStatus;
        this.pnr = pnr;
    }

    @Generated(hash = 351578258)
    public Flight() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getOriginTerminal() {
        return originTerminal;
    }

    public void setOriginTerminal(String originTerminal) {
        this.originTerminal = originTerminal;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getDestinationTerminal() {
        return destinationTerminal;
    }

    public void setDestinationTerminal(String destinationTerminal) {
        this.destinationTerminal = destinationTerminal;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getMarketingAirline() {
        return marketingAirline;
    }

    public void setMarketingAirline(String marketingAirline) {
        this.marketingAirline = marketingAirline;
    }

    public String getOperatingAirline() {
        return operatingAirline;
    }

    public void setOperatingAirline(String operatingAirline) {
        this.operatingAirline = operatingAirline;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
