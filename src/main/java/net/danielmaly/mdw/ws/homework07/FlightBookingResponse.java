package net.danielmaly.mdw.ws.homework07;

public class FlightBookingResponse extends AirlineServiceResponse {
    private FlightBooking flightBooking;

    public FlightBooking getFlightBooking() {
        return flightBooking;
    }

    public void setFlightBooking(FlightBooking flightBooking) {
        this.flightBooking = flightBooking;
    }

}
