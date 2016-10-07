package com.percimal.singaporeairlines;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Flight implements Serializable {

    @Id(autoincrement = true)
    private Long id;

    private static final long serialVersionUID = 1L;

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

    @Generated(hash = 270020408)
    public Flight(Long id, Date departure, Date arrival, String originAirport,
            String originTerminal, String destinationAirport,
            String destinationTerminal, String flightNumber,
            String marketingAirline, String operatingAirline, String bookingCode,
            String travelClass, String bookingStatus, String pnr) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDeparture() {
        return this.departure;
    }
    public void setDeparture(Date departure) {
        this.departure = departure;
    }
    public Date getArrival() {
        return this.arrival;
    }
    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }
    public String getOriginAirport() {
        return this.originAirport;
    }
    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }
    public String getOriginTerminal() {
        return this.originTerminal;
    }
    public void setOriginTerminal(String originTerminal) {
        this.originTerminal = originTerminal;
    }
    public String getDestinationAirport() {
        return this.destinationAirport;
    }
    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
    public String getDestinationTerminal() {
        return this.destinationTerminal;
    }
    public void setDestinationTerminal(String destinationTerminal) {
        this.destinationTerminal = destinationTerminal;
    }
    public String getFlightNumber() {
        return this.flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public String getMarketingAirline() {
        return this.marketingAirline;
    }
    public void setMarketingAirline(String marketingAirline) {
        this.marketingAirline = marketingAirline;
    }
    public String getOperatingAirline() {
        return this.operatingAirline;
    }
    public void setOperatingAirline(String operatingAirline) {
        this.operatingAirline = operatingAirline;
    }
    public String getBookingCode() {
        return this.bookingCode;
    }
    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }
    public String getTravelClass() {
        return this.travelClass;
    }
    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }
    public String getBookingStatus() {
        return this.bookingStatus;
    }
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public String getPnr() {
        return this.pnr;
    }
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
