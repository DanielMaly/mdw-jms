package net.danielmaly.mdw.ws.homework08;

import net.danielmaly.mdw.ws.homework07.FlightBooking;
import net.danielmaly.mdw.ws.tutorial05.TripBooking;

public class BookingCreateResponse {
    private String message;
    private FlightBooking flightBooking;
    private TripBooking tripBooking;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FlightBooking getFlightBooking() {
        return flightBooking;
    }

    public void setFlightBooking(FlightBooking flightBooking) {
        this.flightBooking = flightBooking;
    }

    public TripBooking getTripBooking() {
        return tripBooking;
    }

    public void setTripBooking(TripBooking tripBooking) {
        this.tripBooking = tripBooking;
    }
}
