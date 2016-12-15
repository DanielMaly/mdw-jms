package net.danielmaly.mdw.ws.tutorial05;

import javax.jws.WebService;
import java.util.*;

@WebService
public class Agency {

    private static int idCounter = 0;

    private Map<Integer, Trip> trips = new HashMap<>();
    private List<Booking> bookingList = new ArrayList<>();

    public TripList listTrips() {
        TripList tripList = new TripList();
        tripList.setTrips(new ArrayList<>(trips.values()));
        return tripList;
    }

    public TripCreateResponse addTrip(TripCreateData data) {
        Trip trip = new Trip();
        trip.setCapacity(data.getCapacity());
        trip.setLocation(data.getLocation());
        trip.setId(idCounter++);
        trips.put(trip.getId(), trip);
        TripCreateResponse resp = new TripCreateResponse();
        resp.setTripId(trip.getId());
        return resp;
    }

    public void addBooking(Booking booking) {
        Trip trip = trips.get(booking.getTripId());
        trip.getBookings().add(booking);
        bookingList.add(booking);
    }

}
