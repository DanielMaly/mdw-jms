package net.danielmaly.mdw.ws.tutorial05;

import javax.jws.WebService;

@WebService
public interface IAgency {
    public TripList listTrips();
    public TripCreateResponse addTrip(TripCreateData data);
    public void addBooking(TripBooking tripBooking);
}
